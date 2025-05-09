import { Interface, ModelProperty, Namespace, Operation, Union } from "@typespec/compiler";
import { $ } from "@typespec/compiler/typekit";
import { useStateMap, useStateSet } from "@typespec/compiler/utils";
import type {
  ChannelDecorator,
  InfoDecorator,
  MessagesDecorator,
  ReceiveDecorator,
  SendDecorator,
} from "../generated-defs/TypeSpec.AsyncAPI.js";
import { AsyncAPIStateKeys } from "./lib.js";

//#region @channel
export type ChannelInfo = { address: string; messages: Union };

export const $channelDecorator: ChannelDecorator = (context, target, address, messages) => {
  setChannel(context.program, target, { address, messages });
};

const [getChannel, setChannel, getAllChannels] = useStateMap<Interface, ChannelInfo>(
  AsyncAPIStateKeys.channel,
);

export { getAllChannels, getChannel, setChannel };
//#endregion

//#region @info
export type ServiceInfo = { title?: string; version: string; termsOfService?: string };

export const $infoDecorator: InfoDecorator = (context, target, info) => {
  setInfo(context.program, target, info);
};

const [getInfo, setInfo] = useStateMap<Namespace, ServiceInfo>(AsyncAPIStateKeys.info);

export { getInfo, setInfo };
//#endregion

//#region @receive
const [isReceive, setReceive] = useStateSet<Operation>(AsyncAPIStateKeys.receive);

export const $receiveDecorator: ReceiveDecorator = (context, target) => {
  setReceive(context.program, target);
};

export { isReceive, setReceive };
//#endregion

//#region @send
const [isSend, setSend] = useStateSet<Operation>(AsyncAPIStateKeys.send);

export const $sendDecorator: SendDecorator = (context, target) => {
  setSend(context.program, target);
};

export { isSend, setSend };
//#endregion

//#region @messages
const [isMessages, setMessages] = useStateSet<ModelProperty>(AsyncAPIStateKeys.messages);

export const $messagesDecorator: MessagesDecorator = (context, target) => {
  // TODO: Need to validate that the target is a valid message in the channel
  const tk = $(context.program);
  const propertyType = target.type;
  if (!tk.union.is(propertyType) && !tk.unionVariant.is(propertyType)) {
    // TODO: report diagnostic
    throw new Error("Invalid type for @messages decorator");
  }

  setMessages(context.program, target);
};

export { isMessages, setMessages };
//#endregion
