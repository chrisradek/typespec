async function run() {
  const response = await fetch("http://localhost:1337/channel/1/events", {
    method: "GET",
  });
  if (!response.ok || !response.body) {
    throw new Error(`${response.status}: ${response.statusText}`);
  }

  for await (const json of response.body
    .pipeThrough(new TextDecoderStream())
    .pipeThrough(new JsonlSplitterStream())) {
    console.log(json);
  }

  console.log("done");
}

run().catch(console.error);

class JsonlSplitterStream extends TransformStream<string, JSON> {
  constructor() {
    let buffer = "";
    /** @type {string} */
    super({
      /**
       *
       * @param {string} chunk
       * @param {TransformStreamDefaultController} controller
       */
      async transform(chunk, controller) {
        buffer += chunk;

        const events = buffer.split("\n");

        // If the buffer ends with `"\n"` then we have full events.
        // The last element of the split should be an empty string.
        // Otherwise, the last element is a partial event, so should
        // replace the buffer.
        if (events[-1]) {
          buffer = events.pop()!;
        } else {
          buffer = "";
          events.pop();
        }

        for (const event of events) {
          controller.enqueue(JSON.parse(event));
        }
      },
    });
  }
}
