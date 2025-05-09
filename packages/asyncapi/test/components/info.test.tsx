import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { JsonObject, SourceFile } from "@alloy-js/json";
import { Namespace } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, it } from "vitest";
import { ServiceInfo } from "../../src/components/info.js";
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
    @info(#{ title: "Test Service", version: "1.0.0" })
    namespace TestService;
  `)) as { TestService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, TestService);

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <ServiceInfo service={asyncService} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "info": {
          "title": "Test Service",
          "version": "1.0.0"
        }
      }
    `,
  );
});

it("prefers info title over service title", async () => {
  const { TestService } = (await runner.compile(`
    @test
    @service(#{ title: "Bad Title" })
    @info(#{ title: "Test Service", version: "1.0.0" })
    namespace TestService;
  `)) as { TestService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, TestService);

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <ServiceInfo service={asyncService} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "info": {
          "title": "Test Service",
          "version": "1.0.0"
        }
      }
    `,
  );
});

it("falls back to service title", async () => {
  const { TestService } = (await runner.compile(`
    @test
    @service(#{ title: "Test Service" })
    @info(#{ version: "1.0.0" })
    namespace TestService;
  `)) as { TestService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, TestService);

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <JsonObject>
          <ServiceInfo service={asyncService} />
        </JsonObject>
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "info": {
          "title": "Test Service",
          "version": "1.0.0"
        }
      }
    `,
  );
});
