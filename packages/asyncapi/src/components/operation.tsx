import { For, refkey } from "@alloy-js/core";
import { JsonArray, JsonObject, JsonObjectProperty } from "@alloy-js/json";
import { AsyncOperation } from "../service.js";

export interface OperationsProps {
  operations: AsyncOperation[];
}

export function Operations(props: OperationsProps) {
  return (
    <JsonObjectProperty name="operations">
      <JsonObject>
        <For each={props.operations} comma>
          {(operation) => <Operation operation={operation} />}
        </For>
      </JsonObject>
    </JsonObjectProperty>
  );
}

export interface OperationProps {
  operation: AsyncOperation;
}

export function Operation(props: OperationProps) {
  const operation = props.operation;
  // TODO: namespace nested names?
  const operationName = operation.name;

  const operationKey = refkey(props.operation);

  return (
    <JsonObjectProperty name={operationName}>
      <JsonObject refkey={operationKey}>
        <JsonObjectProperty name="action" jsValue={operation.action} />
        <JsonObjectProperty name="channel">
          <JsonObject>
            <JsonObjectProperty name="$ref">{refkey(operation.channel)}</JsonObjectProperty>
          </JsonObject>
        </JsonObjectProperty>
        <JsonObjectProperty name="messages">
          <JsonArray>
            <For each={operation.messages} comma>
              {(message) => (
                <JsonObject>
                  <JsonObjectProperty name="$ref">
                    {refkey("channel", message.type.type)}
                  </JsonObjectProperty>
                </JsonObject>
              )}
            </For>
          </JsonArray>
        </JsonObjectProperty>
      </JsonObject>
    </JsonObjectProperty>
  );
}
