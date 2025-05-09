import {
  $channelDecorator,
  $infoDecorator,
  $messagesDecorator,
  $receiveDecorator,
  $sendDecorator,
} from "./decorators.js";

export { $lib } from "./lib.js";

export const $decorators = {
  "TypeSpec.AsyncAPI": {
    channel: $channelDecorator,
    info: $infoDecorator,
    messages: $messagesDecorator,
    receive: $receiveDecorator,
    send: $sendDecorator,
  },
};
