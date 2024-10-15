import { createTestLibrary, findTestPackageRoot } from "@typespec/compiler/testing";

export const CjrStreamsDemoTestLibrary = createTestLibrary({
  name: "@typespec/cjr-streams-demo",
  packageRoot: await findTestPackageRoot(import.meta.url),
});
