import {
  getDoc,
  listServices,
  type EmitContext,
  type Model,
  type ModelProperty,
  type Operation,
  type Program,
  type Scalar,
  type StringLiteral,
  type Type,
} from "@typespec/compiler";
import {
  code,
  CodeTypeEmitter,
  createAssetEmitter,
  StringBuilder,
  type Context,
  type EmittedSourceFile,
  type EmitterOutput,
  type SourceFile,
} from "@typespec/compiler/emitter-framework";
import {
  getHttpOperation,
  getServers,
  type HttpOperation,
  type HttpOperationBody,
  type HttpOperationMultipartBody,
} from "@typespec/http";
import * as prettier from "prettier";
import { getBoilerplateFiles } from "./boilerplate.js";
import { getJsTypeForScalar } from "./scalars.js";
import { ensureCleanDirectory } from "./utils.js";

export async function $onEmit(context: EmitContext) {
  const services = listServices(context.program);
  const [service] = services;
  const globalNamespace = context.program.getGlobalNamespaceType();
  const servers = getServers(context.program, service.type);
  const serverEndpoint = servers![0].url;

  class CjrDemoEmitter extends CodeTypeEmitter {
    #packageFiles = getBoilerplateFiles(this.emitter);
    programContext(program: Program): Context {
      const sourceFile = this.emitter.createSourceFile(`index.ts`);
      sourceFile.meta["filetype"] = "typescript";
      sourceFile.imports.set("url-template", ["parseTemplate"]);
      return {
        file: sourceFile,
        scope: sourceFile.globalScope,
      };
    }

    scalarDeclaration(scalar: Scalar, name: string): EmitterOutput<string> {
      return getJsTypeForScalar(this.emitter.getProgram(), scalar);
    }

    stringLiteral(string: StringLiteral): EmitterOutput<string> {
      return this.emitter.result.rawCode(code`"${string.value}"`);
    }

    modelDeclaration(model: Model, name: string): EmitterOutput<string> {
      const doc = getDoc(this.emitter.getProgram(), model);

      const baseModel = model.baseModel;

      return this.emitter.result.declaration(
        name,
        code`
          ${doc ? `/** ${doc} */` : ""}
          export interface ${name}${baseModel ? ` extends ${baseModel.name}` : ""} {
            ${this.emitter.emitModelProperties(model)}
          }
        `,
      );
    }

    modelPropertyLiteral(property: ModelProperty): EmitterOutput<string> {
      const doc = getDoc(this.emitter.getProgram(), property);

      return this.emitter.result.rawCode(
        code`
          ${doc ? `/** ${doc} */` : ""}
          ${property.name}${property.optional ? "?" : ""}: ${this.emitter.emitTypeReference(
            property.type,
          )}
        `,
      );
    }

    modelPropertyReference(property: ModelProperty): EmitterOutput<string> {
      const doc = getDoc(this.emitter.getProgram(), property);

      return this.emitter.result.rawCode(
        code`
          ${doc ? `/** ${doc} */` : ""}
          ${property.name}: ${this.emitter.emitTypeReference(property.type)}
        `,
      );
    }

    arrayLiteral(array: Model, elementType: Type): EmitterOutput<string> {
      return this.emitter.result.rawCode(code`${this.emitter.emitTypeReference(elementType)}[]`);
    }

    #getBodyParameters(body: HttpOperationBody | HttpOperationMultipartBody) {
      const bodyProperty = body.property;
      if (!bodyProperty) {
        // hits this when body is not explicit
        if (body.type.kind === "Model") {
          return body.type;
        }
        return;
      }

      // Hits this when body is decorated with @body
      return bodyProperty;
    }

    #emitHttpOperationParameters(operation: HttpOperation): EmitterOutput<string> {
      const signature = new StringBuilder();

      const requiredParams = new StringBuilder();
      const optionalParams = new StringBuilder();

      for (const parameter of operation.parameters.parameters) {
        const isOptional = parameter.param.optional;
        const jsType = this.emitter.emitTypeReference(parameter.param.type);
        const paramBuilder = isOptional ? optionalParams : requiredParams;

        paramBuilder.push(code`${parameter.param.name}${isOptional ? "?" : ""}: ${jsType},`);
      }

      const body = operation.parameters.body;
      if (body) {
        // emit type reference for each property
        const bodyType = this.#getBodyParameters(body);
        if (bodyType?.kind === "ModelProperty") {
          const isOptional = bodyType.optional;
          const jsType = this.emitter.emitTypeReference(bodyType.type);
          const paramBuilder = isOptional ? optionalParams : requiredParams;

          paramBuilder.push(code`${bodyType.name}${isOptional ? "?" : ""}: ${jsType},`);
        } else if (bodyType?.kind === "Model") {
          for (const prop of bodyType.properties.values()) {
            const isOptional = prop.optional;
            const jsType = this.emitter.emitTypeReference(prop.type);
            const paramBuilder = isOptional ? optionalParams : requiredParams;

            paramBuilder.push(code`${prop.name}${isOptional ? "?" : ""}: ${jsType},`);
          }
        }
      }

      signature.pushStringBuilder(requiredParams);
      signature.pushStringBuilder(optionalParams);
      return signature.reduce();
    }

    #operationHeaderParams(operation: HttpOperation): EmitterOutput<string> {
      const headerParams = operation.parameters.parameters.filter((p) => p.type === "header");
      const requiredHeaders = headerParams.filter((p) => !p.param.optional);
      const optionalHeaders = headerParams.filter((p) => p.param.optional);

      const headersBuilder = new StringBuilder();
      headersBuilder.pushLiteralSegment("const headers: Record<string, string> = {};\n");
      headersBuilder.push("\n");

      for (const header of requiredHeaders) {
        headersBuilder.push(code`headers["${header.name}"] = ${header.param.name};\n`);
      }

      for (const header of optionalHeaders) {
        headersBuilder.push(code`
          if (${header.param.name} !== undefined) {
            headers["${header.name}"] = ${header.param.name};\n
          }
        `);
      }

      // Check if content-type existed...
      if (!headerParams.find((p) => p.name.toLowerCase() === "content-type")) {
        // get it from the body
        const body = operation.parameters.body;
        if (body?.contentTypes) {
          headersBuilder.push(code`headers["Content-Type"] = "${body.contentTypes[0]}";`);
        }
      }

      return headersBuilder.reduce();
    }

    operationDeclaration(operation: Operation, name: string): EmitterOutput<string> {
      const doc = getDoc(this.emitter.getProgram(), operation);
      const [httpOperation] = getHttpOperation(this.emitter.getProgram(), operation);

      // get the declared parameters
      const declParams = this.#emitHttpOperationParameters(httpOperation);
      const pathParamNames = httpOperation.parameters.parameters
        .filter((p) => ["query", "path"].includes(p.type))
        .map((p) => p.param.name);

      const headerBuilder = new StringBuilder();
      for (const header of httpOperation.parameters.parameters.filter((p) => p.type === "header")) {
        headerBuilder.push(code`"${header.name}": ${header.param.name},`);
      }
      const headers = this.#operationHeaderParams(httpOperation);

      return this.emitter.result.declaration(
        name,
        code`
          ${doc ? `/** ${doc} */` : ""}
          export async function ${name}(${declParams}): Promise<void> {
            const path = parseTemplate("${httpOperation.uriTemplate}").expand({${pathParamNames.join(", ")}});

            ${headers}

            return fetch(\`${serverEndpoint}\${path}\`, {
              method: "${httpOperation.verb}",
              headers,
              body: JSON.stringify({}),
            });
          }
        `,
      );
    }

    async sourceFile(sourceFile: SourceFile<string>): Promise<EmittedSourceFile> {
      for (const pFile of this.#packageFiles) {
        if (pFile.source === sourceFile) return pFile.emitted;
      }

      const contents = new StringBuilder();
      for (const i of sourceFile.imports) {
        contents.pushLiteralSegment(`import { ${i[1].join(", ")} } from "${i[0]}";`);
      }

      for (const d of sourceFile.globalScope.declarations) {
        contents.push(d.value);
      }

      let fileContents = contents.segments.join("\n\n");
      if (sourceFile.meta["filetype"] === "typescript") {
        fileContents = await prettier.format(fileContents, { parser: "typescript" });
      }

      return {
        contents: fileContents,
        path: sourceFile.path,
      };
    }

    writeOutput(sourceFiles: SourceFile<string>[]): Promise<void> {
      for (const source of this.#packageFiles) {
        sourceFiles.push(source.source);
      }

      return super.writeOutput(sourceFiles);
    }
  }

  const emitter = createAssetEmitter<string, {}>(context.program, CjrDemoEmitter, context);
  emitter.emitProgram();
  await ensureCleanDirectory(context.program, emitter.getOptions().emitterOutputDir);
  await emitter.writeOutput();
}

// 1. Get all of the operations
