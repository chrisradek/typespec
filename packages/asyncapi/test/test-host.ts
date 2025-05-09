import { createTestHost, createTestWrapper } from "@typespec/compiler/testing";
import { EventsTestLibrary } from "@typespec/events/testing";
import { AsyncAPIEmitterTestLibrary } from "../src/testing/index.js";

export async function createAsyncAPITestHost() {
  return createTestHost({
    libraries: [AsyncAPIEmitterTestLibrary, EventsTestLibrary],
  });
}

export async function createAsyncAPIEmitterTestRunner() {
  const host = await createAsyncAPITestHost();

  return createTestWrapper(host, {
    autoImports: ["@typespec/asyncapi", "@typespec/events"],
    autoUsings: ["AsyncAPI", "Events"],
    compilerOptions: {
      noEmit: false,
      emit: ["@typespec/asyncapi"],
    },
  });
}
