import type {
  DecoratorContext,
  Interface,
  ModelProperty,
  Namespace,
  Operation,
  Union,
} from "@typespec/compiler";

export interface AdditionalInfo {
  readonly title?: string;
  readonly version: string;
  readonly termsOfService?: string;
}

export type ChannelDecorator = (
  context: DecoratorContext,
  target: Interface,
  address: string,
  messages: Union,
) => void;

export type InfoDecorator = (
  context: DecoratorContext,
  target: Namespace,
  additionalInfo: AdditionalInfo,
) => void;

export type SendDecorator = (context: DecoratorContext, target: Operation) => void;

export type ReceiveDecorator = (context: DecoratorContext, target: Operation) => void;

export type MessagesDecorator = (context: DecoratorContext, target: ModelProperty) => void;

export type TypeSpecAsyncAPIDecorators = {
  channel: ChannelDecorator;
  info: InfoDecorator;
  send: SendDecorator;
  receive: ReceiveDecorator;
  messages: MessagesDecorator;
};
