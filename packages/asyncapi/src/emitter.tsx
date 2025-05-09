import * as aj from "@alloy-js/json";
import { EmitContext } from "@typespec/compiler";
import { Output, writeOutput } from "@typespec/emitter-framework";
import { Spec } from "./components/spec.jsx";
import { AsyncApiEmitterOptions } from "./lib.js";
import { getAllAsyncAPIServices } from "./service.js";

/**
 * Main function to handle the emission process.
 * @param context - The context for the emission process.
 */
export async function $onEmit(context: EmitContext<AsyncApiEmitterOptions>) {
  // 1. Grab the service
  const services = getAllAsyncAPIServices(context.program);
  if (!services.length) return;

  // for now only support 1 service;
  if (services.length > 1) throw new Error("Does not support multiple services.");
  const service = services[0];

  const output = (
    <Output program={context.program}>
      <aj.SourceFile path="asyncapi.json">
        <Spec service={service} />
      </aj.SourceFile>
    </Output>
  );

  await writeOutput(context.program, output, context.emitterOutputDir);
}
