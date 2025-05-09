import { createTypeSpecLibrary, JSONSchemaType } from "@typespec/compiler";

export interface AsyncApiEmitterOptions {}

const EmitterOptionsSchema: JSONSchemaType<AsyncApiEmitterOptions> = {
  type: "object",
  additionalProperties: true,
  properties: {},
  required: [],
};

export const $lib = createTypeSpecLibrary({
  name: "@typespec/asyncapi",
  emitter: {
    options: EmitterOptionsSchema,
  },
  diagnostics: {},
  state: {
    channel: { description: "State for the @channel decorator." },
    info: { description: "State for the @info decorator." },
    messages: { description: "State for the @messages decorator." },
    receive: { description: "State for the @receive decorator." },
    send: { description: "State for the @send decorator." },
  },
});

export const { reportDiagnostic, createDiagnostic, stateKeys: AsyncAPIStateKeys } = $lib;
