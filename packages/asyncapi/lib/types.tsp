using Reflection;

namespace TypeSpec.AsyncAPI;

model Message<Payload extends unknown, headers extends Model | null = null> {
  payload: Payload;
  headers: headers;
}
