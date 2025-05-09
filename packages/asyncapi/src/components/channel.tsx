import { For, refkey } from "@alloy-js/core";
import { JsonObject, JsonObjectProperty } from "@alloy-js/json";
import { AsyncChannel } from "../service.js";
import { ChannelMessage } from "./channel-message.jsx";

export interface ChannelsProps {
  channels: AsyncChannel[];
}

export function Channels(props: ChannelsProps) {
  return (
    <JsonObjectProperty name="channels">
      <JsonObject>
        <For each={props.channels} comma>
          {(channel) => <Channel channel={channel} />}
        </For>
      </JsonObject>
    </JsonObjectProperty>
  );
}

export interface ChannelProps {
  channel: AsyncChannel;
}

export function Channel(props: ChannelProps) {
  const channel = props.channel;
  const channelName = channel.interface.name;

  const channelKey = refkey(props.channel);

  return (
    <JsonObjectProperty name={channelName}>
      <JsonObject refkey={channelKey}>
        <JsonObjectProperty name="address" jsValue={channel.address} />
        <JsonObjectProperty name="messages">
          <JsonObject>
            <For each={channel.messages} comma>
              {(message) => <ChannelMessage message={message} />}
            </For>
          </JsonObject>
        </JsonObjectProperty>
      </JsonObject>
    </JsonObjectProperty>
  );
}
