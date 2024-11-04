import { IntrinsicType } from "@typespec/compiler";
import { AssetEmitter, EmitterOutput, TypeEmitter } from "@typespec/compiler/emitter-framework";
import { MetadataInfo } from "@typespec/http";
import { OpenAPI3EmitterOptions, reportDiagnostic } from "./lib.js";
import { ResolvedOpenAPI3EmitterOptions } from "./openapi.js";
import { OpenAPI3SchemaEmitter } from "./schema-emitter.js";
import { VisibilityUsageTracker } from "./visibility-usage.js";
import { XmlModule } from "./xml-module.js";

export function createWrappedOpenAPI31SchemaEmitterClass(
  metadataInfo: MetadataInfo,
  visibilityUsage: VisibilityUsageTracker,
  options: ResolvedOpenAPI3EmitterOptions,
  xmlModule: XmlModule | undefined,
): typeof TypeEmitter<Record<string, any>, OpenAPI3EmitterOptions> {
  return class extends OpenAPI31SchemaEmitter {
    constructor(emitter: AssetEmitter<Record<string, any>, OpenAPI3EmitterOptions>) {
      super(emitter, metadataInfo, visibilityUsage, options, xmlModule);
    }
  };
}

export class OpenAPI31SchemaEmitter extends OpenAPI3SchemaEmitter {
  intrinsic(intrinsic: IntrinsicType, name: string): EmitterOutput<object> {
    switch (name) {
      case "unknown":
        return {};
      case "null":
        return { type: "null" };
    }

    reportDiagnostic(this.emitter.getProgram(), {
      code: "invalid-schema",
      format: { type: name },
      target: intrinsic,
    });
    return {};
  }
}
