import { createServer } from "node:http";
import { createSampleRouter } from "./tsp-output/@typespec/http-server-javascript/http/router.ts";
import { UserMessage } from "./tsp-output/@typespec/http-server-javascript/models/all/sample.ts";

/**
 * @param {number} ms
 * @returns
 */
function sleep(ms) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

const router = createSampleRouter({
  async subscribeToChannel(ctx, channelId) {
    const messageGenerator = async function* () {
      let count = 0;
      while (true) {
        yield {
          _type: "usermessage",
          username: "foo@user",
          time: new Date().toISOString(),
          text: `Message ${count++}`,
        } as UserMessage;
        await sleep(1000);
      }
    };

    return {
      contentType: "application/jsonl",
      body: messageGenerator(),
    };
  },
});

const server = createServer();

server.on("request", router.dispatch);

server.listen(1337, () => {
  console.log(`Server listening on http://localhost:1337`);
});
