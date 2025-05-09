import { refkey } from "@alloy-js/core";
import * as aj from "@alloy-js/json";
import { AsyncMessage } from "../service.js";
import { SchemaExpression } from "./schema/schema-expression.jsx";

export interface MessageDeclarationProps {
  message: AsyncMessage;
}

export function MessageDeclaration(props: MessageDeclarationProps) {
  const message = props.message;
  const name = message.type.name as string;

  // Depending on if the type is a model expression or declaration,
  // Can create a ref
  const messageType = message.type.type;
  const ref = refkey(messageType);

  // some transform of message models - `@data` is the payload

  return (
    <aj.JsonObjectProperty name={name}>
      <aj.JsonObject refkey={ref}>
        <aj.JsonObjectProperty name="payload">
          <SchemaExpression type={message.payloadType} />
        </aj.JsonObjectProperty>
      </aj.JsonObject>
    </aj.JsonObjectProperty>
  );
}
