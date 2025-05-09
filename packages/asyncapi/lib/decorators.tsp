using TypeSpec.Reflection;

namespace TypeSpec.AsyncAPI;

extern dec channel(target: Interface, address: valueof string, messages: Union);

model AdditionalInfo {
  /** The title of the API. Overrides the `@service` title. */
  title?: string;

  /** The version of the OpenAPI document (which is distinct from the OpenAPI Specification version or the API implementation version). */
  version: string;

  /** A URL to the Terms of Service for the API. MUST be in the format of a URL. */
  termsOfService?: url;
}

extern dec info(target: Namespace, additionalInfo: valueof AdditionalInfo);

extern dec send(target: Operation);

extern dec receive(target: Operation);

extern dec messages(target: ModelProperty);
