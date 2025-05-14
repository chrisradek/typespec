import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { JsonObject, SourceFile } from "@alloy-js/json";
import { Namespace } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, expect, it } from "vitest";
import { MessageDeclaration } from "../../src/components/message-declaration.jsx";
import { getAsyncAPIService } from "../../src/service.js";
import { createAsyncAPIEmitterTestRunner } from "../test-host.js";
import { assertFileContents } from "../utils.js";

let runner: BasicTestRunner;

beforeEach(async () => {
  runner = await createAsyncAPIEmitterTestRunner();
});

it("works", async () => {
  const { TestService } = (await runner.compile(`
    @test
    @service
    @info(#{ version: "1.0.0", title: "Test Service" })
    namespace TestService;

    @channel("test-channel", TestMessages)
    interface TestChannel {
    
    }

    @events
    union TestMessages {
      ping: PingMessage,
    }

    model PingMessage {
      @data payload: { timestamp: utcDateTime }
    }
  `)) as { TestService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, TestService);
  expect(asyncService.messages.length).toBe(1);
  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <MessageDeclaration message={asyncService.messages[0]} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "ping": {
          "payload": {
            "type": "object",
            "properties": {
              "timestamp": {
                "type": "string",
                "format": "date-time"
              }
            },
            "required": ["timestamp"]
          }
        }
      }
    `,
  );
});
