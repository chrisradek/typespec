import { JsonObject, JsonObjectProperty } from "@alloy-js/json";
import { Model, Namespace, navigateTypesInNamespace, Program, Type } from "@typespec/compiler";
import { $ } from "@typespec/compiler/typekit";
import { useTsp } from "@typespec/emitter-framework";
import { isEvents } from "@typespec/events";
import { AsyncService } from "../service.js";
import { Channels } from "./channel.jsx";
import { Components } from "./components.jsx";
import { ServiceInfo } from "./info.jsx";
import { Operations } from "./operation.jsx";

export interface SpecProps {
  service: AsyncService;
}

export function Spec(props: SpecProps) {
  const { $ } = useTsp();
  const service = props.service;

  const messageDeclarationModels = new Set<Model>(
    service.messages.map((m) => m.type.type as Model),
  );
  const { componentSchemas } = gatherComponents(
    $.program,
    service.namespace,
    messageDeclarationModels,
  );

  return (
    <JsonObject>
      <JsonObjectProperty name="asyncapi" jsValue={"3.0.0"} />
      <ServiceInfo service={service} />
      <Channels channels={service.channels} />
      <Operations operations={service.operations} />
      <Components messages={service.messages} schemas={componentSchemas} />
    </JsonObject>
  );
}

function gatherComponents(program: Program, namespace: Namespace, componentMessages: Set<Model>) {
  const tk = $(program);
  const componentSchemas = new Set<Type>();

  // 1st, collect messages
  // navigateTypesInNamespace(namespace, {
  //   union(context) {
  //     if (isEvents(program, context)) {
  //       for (const variant of context.variants.values()) {
  //         if (tk.model.is(variant.type) && !tk.model.isExpresion(variant.type)) {
  //           componentMessages.add(variant.type);
  //         }
  //       }
  //     }
  //     return ListenerFlow.NoRecursion;
  //   },
  // });

  // 2nd, collect schemas
  navigateTypesInNamespace(namespace, {
    model(context) {
      if (!tk.model.isExpresion(context) && !componentMessages.has(context)) {
        componentSchemas.add(context);
      }
    },
    scalar(context) {
      componentSchemas.add(context);
    },
    enum(context) {
      componentSchemas.add(context);
    },
    union(context) {
      if (!isEvents(program, context) && !tk.union.isExpression(context)) {
        componentSchemas.add(context);
      }
    },
  });

  return { componentSchemas };
}
