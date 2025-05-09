import { reactive, refkey } from "@alloy-js/core";
import {
  ArrayModelType,
  Enum,
  Model,
  Scalar,
  serializeValueAsJson,
  Type,
  Union,
} from "@typespec/compiler";
import { Typekit } from "@typespec/compiler/typekit";

export function getSchema($: Typekit, type: Type) {
  if ($.scalar.is(type)) {
    return getSchemaForStdScalars($, type);
  }

  if ($.model.is(type)) {
    if (!$.model.isExpresion(type)) {
      return { $ref: refkey(type) };
    }
    return getSchemaForModel($, type);
  }

  if ($.record.is(type)) {
    return getSchemaForModel($, type);
  }

  if ($.array.is(type)) {
    if (!$.model.isExpresion(type)) {
      return { $ref: refkey(type) };
    }
    return getSchemaForArray($, type);
  }

  if ($.enum.is(type)) {
    if (type.name) {
      return { $ref: refkey(type) };
    }
    return getSchemaForEnum($, type);
  }

  if ($.union.is(type)) {
    if (!$.union.isExpression(type)) {
      return { $ref: refkey(type) };
    }
    return getSchemaForUnion($, type);
  }

  return {};
}

export interface SchemaDeclarationProps {
  type: Model;
  name?: string;
}

export function getSchemaForArray($: Typekit, array: ArrayModelType) {
  const arraySchema = { type: "array" } as Record<string, any>;

  const elementType = $.array.getElementType(array);
  arraySchema.items = getSchema($, elementType);
  // TODO: apply constraints

  return arraySchema;
}

export function getSchemaForModel($: Typekit, model: Model) {
  const objectSchema = reactive({} as Record<string, unknown>);

  objectSchema.type = "object";

  const modelProperties = $.model.getProperties(model, { includeExtended: false });
  if (modelProperties.size) {
    const required: string[] = [];
    objectSchema.properties = Array.from(modelProperties.entries()).reduce(
      (acc, [name, modelProp]) => {
        if (!modelProp.optional) required.push(name);
        acc[name] = getSchema($, modelProp.type);

        if (modelProp.defaultValue) {
          acc[name].default = serializeValueAsJson($.program, modelProp.defaultValue, modelProp);
        }

        // TODO: handle anyOf/oneOf
        // TODO: Apply constraints
        return acc;
      },
      {} as Record<string, any>,
    );
    if (required.length) {
      objectSchema.required = required;
    }
  }

  // TODO: handle allOf
  // TODO: apply constraints

  // Apply indexer
  const indexer = $.model.getIndexType(model);
  if (indexer) {
    const indexType = $.record.getElementType(indexer);
    objectSchema.additionalProperties = getSchema($, indexType);
  }
  return objectSchema;
}

export function getSchemaForEnum($: Typekit, en: Enum) {
  const enumTypes = new Set<string>();
  const enumValues = new Set<string | number>();
  for (const member of en.members.values()) {
    enumTypes.add(typeof member.value === "number" ? "number" : "string");
    enumValues.add(member.value ?? member.name);
  }

  const enumTypesArray = [...enumTypes];
  const enumSchema = {
    type: enumTypesArray.length === 1 ? enumTypesArray[0] : enumTypesArray,
    enum: [...enumValues],
  };

  // TODO: apply constraints
  return enumSchema;
}

export function getSchemaForUnion($: Typekit, union: Union) {
  // TODO: Implement oneOf decorator
  const ofKey = "anyOf";

  // TODO: apply constraints on each variant
  const variantsSchemas: any[] = Array.from(union.variants.values()).map((v) => getSchema($, v));

  const unionSchema = { [ofKey]: variantsSchemas };

  // TODO: apply constraints on union

  return unionSchema;
}

export function getSchemaForScalar($: Typekit, scalar: Scalar) {
  let result: Record<string, any> = {};

  const isStd = isStdType($, scalar);
  if (isStd) {
    result = getSchemaForStdScalars($, scalar);
  } else if (scalar.baseScalar) {
    result = getSchemaForScalar($, scalar.baseScalar);
  } else {
    // TODO: report diagnostic
    return {};
  }

  // TODO: apply constraints
  if (isStd) {
    delete result.description;
  }

  return result;
}

export function isStdType($: Typekit, type: Type) {
  return $.program.checker.isStdType(type);
}

export function getSchemaForStdScalars($: Typekit, scalar: Scalar) {
  if ($.scalar.isBoolean(scalar)) {
    return { type: "boolean" };
  } else if ($.scalar.isBytes(scalar)) {
    return { type: "string", format: "byte" };
  } else if ($.scalar.isNumeric(scalar)) {
    return { type: "number" };
  } else if ($.scalar.isDecimal(scalar)) {
    return { type: "number", format: "decimal" };
  } else if ($.scalar.isDecimal128(scalar)) {
    return { type: "number", format: "decimal128" };
  } else if ($.scalar.isFloat(scalar)) {
    return { type: "number" };
  } else if ($.scalar.isFloat32(scalar)) {
    return { type: "number", format: "float" };
  } else if ($.scalar.isFloat64(scalar)) {
    return { type: "number", format: "double" };
  } else if ($.scalar.isInteger(scalar)) {
    return { type: "integer" };
  } else if ($.scalar.isInt8(scalar)) {
    return { type: "integer", format: "int8" };
  } else if ($.scalar.isInt16(scalar)) {
    return { type: "integer", format: "int16" };
  } else if ($.scalar.isInt32(scalar)) {
    return { type: "integer", format: "int32" };
  } else if ($.scalar.isInt64(scalar)) {
    return { type: "integer", format: "int64" };
  } else if ($.scalar.isSafeint(scalar)) {
    return { type: "integer", format: "int64" };
  } else if ($.scalar.isUint8(scalar)) {
    return { type: "integer", format: "uint8" };
  } else if ($.scalar.isUint16(scalar)) {
    return { type: "integer", format: "uint16" };
  } else if ($.scalar.isUint32(scalar)) {
    return { type: "integer", format: "uint32" };
  } else if ($.scalar.isUint64(scalar)) {
    return { type: "integer", format: "uint64" };
  } else if ($.scalar.isString(scalar)) {
    return { type: "string" };
  } else if ($.scalar.isOffsetDateTime(scalar) || $.scalar.isUtcDateTime(scalar)) {
    return { type: "string", format: "date-time" };
  } else if ($.scalar.isPlainDate(scalar)) {
    return { type: "string", format: "date" };
  } else if ($.scalar.isPlainTime(scalar)) {
    return { type: "string", format: "time" };
  } else if ($.scalar.isDuration(scalar)) {
    return { type: "string", format: "duration" };
  } else if ($.scalar.isUrl(scalar)) {
    return { type: "string", format: "uri" };
  }
  return {};
}
