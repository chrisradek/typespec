import type { Program, Type } from "@typespec/compiler";
import type { HttpOperation, HttpOperationResponseContent } from "@typespec/http";
import { getStreamOf } from "@typespec/streams";

export interface FlatResponse {
  statusCode: number;
  type: Type;
  contentType?: string;
  bodyType?: Type;
  streamType?: Type;
  response: HttpOperationResponseContent;
}

export function flattenResponses(program: Program, operation: HttpOperation): FlatResponse[] {
  const flatResponses: FlatResponse[] = [];

  for (const responses of operation.responses) {
    const statusCode = responses.statusCodes;
    const type = responses.type;
    const response = responses.responses[0];

    const body = response.body;
    const streamType = type?.kind === "Model" ? getStreamOf(program, type) : undefined;
    let contentType = body?.contentTypes?.[0];
    if (body && !contentType) {
      contentType = streamType ? "application/jsonl" : "application/json";
    }

    flatResponses.push({
      statusCode: statusCode as number,
      type,
      response,
      streamType,
      contentType,
    });
  }

  return flatResponses;
}
