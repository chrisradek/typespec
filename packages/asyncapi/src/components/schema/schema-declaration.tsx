import { refkey } from "@alloy-js/core";
import * as aj from "@alloy-js/json";
import {
  Enum,
  EnumMember,
  IntrinsicType,
  Model,
  ModelProperty,
  Scalar,
  Union,
  UnionVariant,
} from "@typespec/compiler";
import { useTsp } from "@typespec/emitter-framework";
import { FromJsonWithRefs } from "./from-json-with-refs.jsx";
import {
  getSchemaForEnum,
  getSchemaForModel,
  getSchemaForScalar,
  getSchemaForUnion,
  isStdType,
} from "./transforms.js";

export interface SchemaDeclarationProps {
  type: Enum | EnumMember | IntrinsicType | Model | ModelProperty | Scalar | Union | UnionVariant;
  name?: string;
}

export function SchemaDeclaration(props: SchemaDeclarationProps) {
  const { $ } = useTsp();

  const type = props.type;
  const name = props.name ?? props.type.name;

  if (typeof name !== "string") {
    throw new Error(`Invalid schema declaration`);
  }

  let schema: Record<string, unknown> = {};
  if ($.model.is(type)) {
    schema = getSchemaForModel($, type);
  } else if ($.scalar.is(type) && !isStdType($, type)) {
    schema = getSchemaForScalar($, type);
  } else if ($.enum.is(type)) {
    schema = getSchemaForEnum($, type);
  } else if ($.union.is(type) && !$.union.isExpression(type)) {
    schema = getSchemaForUnion($, type);
  }

  const key = refkey(props.type);
  return (
    <aj.JsonObjectProperty name={name}>
      <FromJsonWithRefs refkey={key} jsValue={schema} />
    </aj.JsonObjectProperty>
  );
}
