import "@typespec/events";
import "@typespec/http/streams";

using Events;
using Http;
using Http.Streams;

@service({
  title: "Sample Service",
})
namespace Sample;

@discriminator("type")
model ChannelEvent {
  type: string;
}

model UserConnect extends ChannelEvent {
  type: "userconnect";
  username: string;
  time: string;
}

model UserMessage extends ChannelEvent {
  type: "usermessage";
  username: string;
  time: string;
  text: string;
}

model UserDisconnect extends ChannelEvent {
  type: "userdisconnect";
  username: string;
  time: string;
}

@events
union ChannelEvents {
  UserConnect,
  UserMessage,
  UserDisconnect,
}

@route("/channel/{channelId}/events")
@get
op subscribeToChannel(@path channelId: string): JsonlStream<ChannelEvents>;
