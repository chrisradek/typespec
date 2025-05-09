import { For } from "@alloy-js/core";
import { JsonObject, JsonObjectProperty } from "@alloy-js/json";
import { Type } from "@typespec/compiler";
import { AsyncMessage } from "../service.js";
import { MessageDeclaration } from "./message-declaration.jsx";
import { SchemaDeclaration } from "./schema/schema-declaration.jsx";

export interface ComponentsProps {
  messages: AsyncMessage[];
  schemas: Set<Type>;
}

export function Components(props: ComponentsProps) {
  return (
    <JsonObject>
      <JsonObjectProperty name="components">
        <JsonObject>
          <Messages messages={props.messages} />
          <Schemas schemas={props.schemas} />
        </JsonObject>
      </JsonObjectProperty>
    </JsonObject>
  );
}

interface MessagesProps {
  messages: AsyncMessage[];
}

function Messages(props: MessagesProps) {
  return (
    <JsonObjectProperty name="messages">
      <JsonObject>
        <For each={props.messages}>{(msg) => <MessageDeclaration message={msg} />}</For>
      </JsonObject>
    </JsonObjectProperty>
  );
}

interface SchemasProps {
  schemas: Set<Type>;
}

function Schemas(props: SchemasProps) {
  return (
    <JsonObjectProperty name="schemas">
      <JsonObject>
        <For each={Array.from(props.schemas)}>
          {(schema) => <SchemaDeclaration type={schema as any} />}
        </For>
      </JsonObject>
    </JsonObjectProperty>
  );
}
