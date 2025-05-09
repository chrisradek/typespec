import { Type } from "@typespec/compiler";
import { useTsp } from "@typespec/emitter-framework";
import { FromJsonWithRefs } from "./from-json-with-refs.jsx";
import { getSchema } from "./transforms.js";

export interface SchemaExpressionProps {
  type: Type;
}

export function SchemaExpression(props: SchemaExpressionProps) {
  const { $ } = useTsp();

  const schema = getSchema($, props.type);

  return <FromJsonWithRefs jsValue={schema} />;
}
