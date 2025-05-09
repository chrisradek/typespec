import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { JsonObject, SourceFile } from "@alloy-js/json";
import { Model, Scalar } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, it } from "vitest";
import { SchemaDeclaration } from "../src/components/schema/schema-declaration.jsx";
import { createAsyncAPIEmitterTestRunner } from "./test-host.js";
import { assertFileContents } from "./utils.js";

let runner: BasicTestRunner;

beforeEach(async () => {
  runner = await createAsyncAPIEmitterTestRunner();
});

it("works", async () => {
  const { FooScalar, Foo, Bar } = (await runner.compile(`
    @test
    @service
    @info(#{ version: "1.0.0", title: "Test Service" })
    namespace TestService;

    @test
    model Foo {
      id: string = "1234";
    }

    @test
    scalar FooScalar extends string;

    @test
    model Bar {
      foo: Foo;
    }
  `)) as { Foo: Model; Bar: Model; FooScalar: Scalar };
  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <SchemaDeclaration type={Foo} />
          <SchemaDeclaration type={Bar} />
          <SchemaDeclaration type={FooScalar} />
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
              "type": "string",
              "default": "1234"
            }
          },
          "required": ["id"]
        },
        "Bar": {
          "type": "object",
          "properties": {
            "foo": {
              "$ref": "#/Foo"
            }
          },
          "required": ["foo"]
        },
        "FooScalar": {
          "type": "string"
        }
      }
    `,
  );
});
