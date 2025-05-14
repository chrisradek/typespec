import {
  Interface,
  isTemplateDeclaration,
  listServices,
  Model,
  ModelProperty,
  Namespace,
  Operation,
  Program,
  Union,
  UnionVariant,
} from "@typespec/compiler";
import { $ } from "@typespec/compiler/typekit";
import { unsafe_getEventDefinitions } from "@typespec/events/experimental";
import { getAllChannels, isMessages, isReceive, isSend } from "./decorators.js";

export type AsyncService = {
  namespace: Namespace;
  channels: AsyncChannel[];
  operations: AsyncOperation[];
  messages: AsyncMessage[];
};

export type AsyncChannel = {
  interface: Interface;
  address: string;
  // parameters
  messages: AsyncMessage[];
};

export type AsyncMessage = {
  type: UnionVariant;
  name: string;
  isRef: boolean;
  payloadType: ModelProperty;
  payloadContentType?: string;
  headers: ModelProperty[];
};

export type AsyncOperation = {
  readonly operation: Operation;
  readonly name: string;
  readonly action: "send" | "receive";
  readonly channel: AsyncChannel;
  readonly messages: AsyncMessage[];
};

export function getAllAsyncAPIServices(program: Program): AsyncService[] {
  const serviceNamespaces = listServices(program);

  return serviceNamespaces.map((service) => getAsyncAPIService(program, service.type));
}

export function getAsyncAPIService(program: Program, serviceNamespace: Namespace): AsyncService {
  const tk = $(program);
  // get the list of channels
  const channels = listChannelsIn(program, serviceNamespace);

  const operations: AsyncOperation[] = [];
  const messages: AsyncMessage[] = [];
  for (const c of channels) {
    const typeToMessage = new Map<UnionVariant, AsyncMessage>();

    // Get all service-level message declarations
    for (const m of c.messages) {
      if (m.isRef) {
        messages.push(m);
      }
      typeToMessage.set(m.type, m);
    }

    // Get all operations for this channel
    for (const op of c.interface.operations.values()) {
      const action = isSend(program, op) ? "send" : isReceive(program, op) ? "receive" : undefined;
      if (!action) {
        // TODO: add a diagnostic
        continue;
      }

      let opMessages: AsyncMessage[] = [];
      for (const prop of op.parameters.properties.values()) {
        if (!isMessages(program, prop)) continue;
        const type = prop.type;

        const variants = tk.unionVariant.is(type)
          ? [type]
          : tk.union.is(type)
            ? tk.union.isExpression(type)
              ? Array.from(type.variants.values()).map((v) => v.type)
              : Array.from(type.variants.values())
            : [];

        // find the message for this type
        opMessages = variants
          .map((v) => typeToMessage.get(v))
          .filter((m): m is AsyncMessage => !!m);
      }

      operations.push({
        operation: op,
        name: op.name,
        action,
        channel: c,
        messages: opMessages,
      });
    }
  }

  return {
    channels,
    namespace: serviceNamespace,
    messages,
    operations,
  };
}

function listChannelsIn(program: Program, namespace: Namespace): AsyncChannel[] {
  const channels: AsyncChannel[] = [];
  // get just the channels that belong to the service namespace
  for (const [iface, info] of getAllChannels(program).entries()) {
    if (isTemplateDeclaration(iface)) continue;
    if (!interfaceInNamespace(iface, namespace)) continue;
    channels.push({
      interface: iface,
      address: info.address,
      messages: convertUnionMessages(program, info.messages),
    });
  }

  return channels;
}

export function convertUnionMessages(program: Program, messages: Union): AsyncMessage[] {
  const asyncMessages: AsyncMessage[] = [];

  const [eventDefinitions] = unsafe_getEventDefinitions(program, messages);

  for (const eventDef of eventDefinitions) {
    if (!eventDef.eventType) {
      throw new Error(`I should be a diagnostic warning of unnamed message`);
    }

    const isRef = (eventDef.type as Model).name !== "";

    asyncMessages.push({
      isRef,
      name: eventDef.eventType,
      type: eventDef.root,
      payloadType: eventDef.payloadType as ModelProperty,
      payloadContentType: eventDef.contentType,
      headers: [],
    });
  }

  return asyncMessages;
}

function interfaceInNamespace(iface: Interface, namespace: Namespace): boolean {
  let parent = iface.namespace;
  while (parent) {
    if (parent === namespace) return true;
    parent = parent.namespace;
  }
  return false;
}
