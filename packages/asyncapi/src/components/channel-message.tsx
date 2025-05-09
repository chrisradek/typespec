import { refkey } from "@alloy-js/core";
import * as aj from "@alloy-js/json";
import { AsyncMessage } from "../service.js";
import { SchemaExpression } from "./schema/schema-expression.jsx";

export interface ChannelMessageProps {
  message: AsyncMessage;
}

export function ChannelMessage(props: ChannelMessageProps) {
  const message = props.message;
  const name = message.type.name as string;

  const messageType = message.type.type;

  // Operations will always reference a Message from a channel,
  // so channel messages must be referenceable
  const ref = refkey("channel", messageType);

  if (message.isRef) {
    const componentRef = refkey(messageType);
    return (
      <aj.JsonObjectProperty name={name}>
        <aj.JsonObject refkey={ref}>
          <aj.JsonObjectProperty name="$ref">{componentRef}</aj.JsonObjectProperty>
        </aj.JsonObject>
      </aj.JsonObjectProperty>
    );
  }

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
