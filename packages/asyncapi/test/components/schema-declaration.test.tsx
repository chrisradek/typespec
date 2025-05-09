import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { JsonObject, SourceFile } from "@alloy-js/json";
import { Model } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, it } from "vitest";
import { SchemaDeclaration } from "../../src/components/schema/schema-declaration.jsx";
import { createAsyncAPIEmitterTestRunner } from "../test-host.js";
import { assertFileContents } from "../utils.js";

let runner: BasicTestRunner;

beforeEach(async () => {
  runner = await createAsyncAPIEmitterTestRunner();
});

it("works", async () => {
  const { Foo } = (await runner.compile(`
    @test
    @service
    namespace TestService;

    @test
    model Foo {
      id: string;
    }
  `)) as { Foo: Model };

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <SchemaDeclaration type={Foo} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "Foo": {
          "type": "object",
          "properties": {
            "id": {
              "type": "string"
            }
          },
          "required": ["id"]
        }
      }
    `,
  );
});
