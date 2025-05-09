import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { JsonObject, SourceFile } from "@alloy-js/json";
import { Namespace } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, it } from "vitest";
import { Channel } from "../../src/components/channel.jsx";
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

    union TestMessages {}
  `)) as { TestService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, TestService);

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <Channel channel={asyncService.channels[0]} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "TestChannel": {
          "address": "test-channel",
          "messages": {

          }
        }
      }
    `,
  );
});
