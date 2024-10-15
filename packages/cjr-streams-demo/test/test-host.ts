import { createTestHost, createTestWrapper } from "@typespec/compiler/testing";
import { CjrStreamsDemoTestLibrary } from "../src/testing/index.js";

export async function createCjrStreamsDemoTestHost() {
  return createTestHost({
    libraries: [CjrStreamsDemoTestLibrary],
  });
}

export async function createCjrStreamsDemoTestRunner() {
  const host = await createCjrStreamsDemoTestHost();
  return createTestWrapper(host, { autoUsings: ["TypeSpec.CJR"] });
}
