import { resolvePath } from "@typespec/compiler";
import { createTestLibrary, TypeSpecTestLibrary } from "@typespec/compiler/testing";
import { fileURLToPath } from "url";

export const AsyncAPIEmitterTestLibrary: TypeSpecTestLibrary = createTestLibrary({
  name: "@typespec/asyncapi",
  packageRoot: resolvePath(fileURLToPath(import.meta.url), "../../../"),
});
