import { createTypeSpecLibrary } from "@typespec/compiler";

export const $lib = createTypeSpecLibrary({
  name: "@typespec/cjr-streams-demo",
  diagnostics: {},
  state: {},
});

export const { reportDiagnostic, createDiagnostic, stateKeys: CjrStreamsDemoStateKeys } = $lib;
