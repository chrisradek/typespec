import { For, isRefkey, Refkey } from "@alloy-js/core";
import { JsonArray, JsonObject, JsonObjectProperty, JsonValue } from "@alloy-js/json";

export interface FromJsonWithRefsProps {
  jsValue: any;
  refkey?: Refkey;
}

export function FromJsonWithRefs(props: FromJsonWithRefsProps) {
  const jsValue = props.jsValue;

  if (typeof jsValue === "undefined") {
    return <></>;
  }

  if (isRefkey(jsValue)) {
    return <>{jsValue}</>;
  }

  if (typeof jsValue !== "object" || jsValue === null) {
    return <JsonValue refkey={props.refkey} jsValue={jsValue} />;
  }

  if (Array.isArray(jsValue)) {
    return (
      <JsonArray refkey={props.refkey}>
        <For each={jsValue} comma>
          {(item) => <FromJsonWithRefs jsValue={item} />}
        </For>
      </JsonArray>
    );
  }

  return (
    <JsonObject refkey={props.refkey}>
      <For each={Array.from(Object.entries(jsValue))} comma>
        {([key, value]) => (
          <JsonObjectProperty name={key}>
            <FromJsonWithRefs jsValue={value} />
          </JsonObjectProperty>
        )}
      </For>
    </JsonObject>
  );
}
