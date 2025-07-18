import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { SourceFile } from "@alloy-js/typescript";
import type { Namespace } from "@typespec/compiler";
import { format } from "prettier";
import { assert, describe, expect, it } from "vitest";
import { Output } from "../../../src/core/components/output.jsx";
import { FunctionDeclaration } from "../../../src/typescript/components/function-declaration.js";
import { getProgram } from "../test-host.js";
describe("Typescript Function Declaration", () => {
  describe("Function bound to Typespec Types", () => {
    describe("Bound to Operation", () => {
      it("creates a function", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op getName(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(`function getName(id: string): string{}`, {
          parser: "typescript",
        });
        expect(actualContent).toBe(expectedContent);
      });

      it("creates a function with JSDoc", async () => {
        const program = await getProgram(`
        namespace DemoService;
        /**
         * This is a test function
         */
        op getName(
        @doc("This is the id")
        id: string, name: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(
          `
          /**
           * This is a test function
           * 
           * @param {string} id - This is the id
           * @param {string} name
           */
          function getName(id: string, name: string): string{}`,
          {
            parser: "typescript",
          },
        );
        expect(actualContent).toBe(expectedContent);
      });

      it("creates a function with overridden JSDoc", async () => {
        const program = await getProgram(`
        namespace DemoService;
        /**
         * This is a test function
         */
        op getName(id: string): string;`);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration doc={["This is a custom description"]} type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(
          `
          /**
           * This is a custom description
           *
           * @param {string} id
           */
          function getName(id: string): string{}`,
          {
            parser: "typescript",
          },
        );
        expect(actualContent).toBe(expectedContent);
      });

      it("creates an async function", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op getName(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration async type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(
          d`async function getName(id: string): Promise<string> {
          
          
          }`,
          {
            parser: "typescript",
          },
        );
        expect(actualContent).toBe(expectedContent);
      });

      it("exports a function", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op getName(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration export type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(`export function getName(id: string): string{}`, {
          parser: "typescript",
        });
        expect(actualContent).toBe(expectedContent);
      });

      it("can override name", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op getName(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration name="newName" type={operation} />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(`function newName(id: string): string{}`, {
          parser: "typescript",
        });
        expect(actualContent).toBe(expectedContent);
      });

      it("can append extra parameters with raw params provided", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op createPerson(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration
                type={operation}
                parameters={[
                  { name: "name", type: "string" },
                  { name: "age", type: "number" },
                ]}
              />
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });
        const expectedContent = await format(
          `function createPerson(name: string, age: number, id: string): string{}`,
          {
            parser: "typescript",
          },
        );
        expect(actualContent).toBe(expectedContent);
      });

      it.skip("can override parameters with an array of ModelProperties", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op createPerson(id: string): string;

        model Foo {
          name: string;
          age: int32;
        }
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];
        const model = Array.from((namespace as Namespace).models.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration type={operation}>
                <FunctionDeclaration.Parameters type={model} />
              </FunctionDeclaration>
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });

        const expectedContent = await format(
          `function createPerson(name: string, age: number): string{}`,
          { parser: "typescript" },
        );

        expect(actualContent).toBe(expectedContent);
      });

      it("can render function body", async () => {
        const program = await getProgram(`
        namespace DemoService;
        op createPerson(id: string): string;
        `);

        const [namespace] = program.resolveTypeReference("DemoService");
        const operation = Array.from((namespace as Namespace).operations.values())[0];

        const res = render(
          <Output program={program}>
            <SourceFile path="test.ts">
              <FunctionDeclaration export type={operation}>
                const message = "Hello World!"; console.log(message);
              </FunctionDeclaration>
            </SourceFile>
          </Output>,
        );

        const testFile = res.contents.find((file) => file.path === "test.ts");
        assert(testFile, "test.ts file not rendered");
        const actualContent = await format(testFile.contents as string, { parser: "typescript" });

        const expectedContent = await format(
          `export function createPerson(id: string): string {
               const message = "Hello World!";
               console.log(message);
           }`,
          { parser: "typescript" },
        );

        expect(actualContent).toBe(expectedContent);
      });
    });
  });
});
