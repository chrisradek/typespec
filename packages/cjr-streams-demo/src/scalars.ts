import type { IntrinsicScalarName, Program, Scalar } from "@typespec/compiler";

export function getJsTypeForScalar(program: Program, scalar: Scalar): string {
  if (program.checker.isStdType(scalar)) {
    return getJsTypeForStdScalar(program, scalar);
  }

  if (scalar.baseScalar) {
    return getJsTypeForScalar(program, scalar.baseScalar);
  }

  return "unknown";
}

export function getJsTypeForStdScalar(program: Program, scalar: Scalar): string {
  const name = scalar.name as IntrinsicScalarName;
  switch (name) {
    case "boolean":
      return "boolean";
    case "bytes":
      return "Uint8Array";
    case "decimal":
    case "decimal128":
    case "float":
    case "float32":
    case "float64":
    case "int8":
    case "int16":
    case "int32":
    case "int64":
    case "numeric":
    case "integer":
    case "uint8":
    case "uint16":
    case "uint32":
    case "uint64":
      return "number";
    case "safeint":
      return "bigint";
    case "string":
    case "url":
    case "duration":
      return "string";
    case "offsetDateTime":
    case "plainDate":
    case "plainTime":
    case "utcDateTime":
      return "Date";
    default:
      throw new Error(`Unknown scalar type: ${name}`);
  }
}
