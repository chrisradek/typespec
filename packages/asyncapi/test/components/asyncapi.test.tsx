import { render } from "@alloy-js/core";
import { d } from "@alloy-js/core/testing";
import { SourceFile } from "@alloy-js/json";
import { Namespace } from "@typespec/compiler";
import { BasicTestRunner } from "@typespec/compiler/testing";
import { Output } from "@typespec/emitter-framework";
import { beforeEach, it } from "vitest";
import { Spec } from "../../src/components/spec.jsx";
import { getAsyncAPIService } from "../../src/service.js";
import { createAsyncAPIEmitterTestRunner } from "../test-host.js";
import { assertFileContents } from "../utils.js";

let runner: BasicTestRunner;

beforeEach(async () => {
  runner = await createAsyncAPIEmitterTestRunner();
});

it("works", async () => {
  const { AccountService } = (await runner.compile(`
    @test
    /**
     * This service is in charge of processing user signups
     */
    @service
    @info(#{ version: "1.0.0", title: "Account Service" })
    namespace AccountService;

    @channel("user/signedup", UserSignedupChannelMessages)
    interface userSignedup {
      @send sendUserSignedUp(@messages messages: UserSignedupChannelMessages): void;
    }

    @events
    union UserSignedupChannelMessages {
      UserSignedUp: UserSignedUp;
      inline: { @data payload: { id: string }; }
    }

    model UserSignedUp {
      @data payload: User;
    }

    model User {
      /**
       * Name of the user
       */
      displayName?: string;
      
      /**
       * Email of the user
       */
      @format("email")
      email?: string;
    }
  `)) as { AccountService: Namespace };
  const asyncService = getAsyncAPIService(runner.program, AccountService);

  const res = render(
    <Output program={runner.program}>
      <SourceFile path="test.json">
        <Spec service={asyncService} />
      </SourceFile>
    </Output>,
  );

  assertFileContents(
    res,
    d`
      {
        "asyncapi": "3.0.0",
        "info": {
          "title": "Account Service",
          "version": "1.0.0"
        },
        "channels": {
          "userSignedup": {
            "address": "user/signedup",
            "messages": {
              "UserSignedUp": {
                "$ref": "#/components/messages/UserSignedUp"
              },
              "inline": {
                "payload": {
                  "type": "object",
                  "properties": {
                    "id": {
                      "type": "string"
                    }
                  },
                  "required": ["id"]
                }
              }
            }
          }
        },
        "operations": {
          "sendUserSignedUp": {
            "action": "send",
            "channel": {
              "$ref": "#/channels/userSignedup"
            },
            "messages": [
              {
                "$ref": "#/channels/userSignedup/messages/UserSignedUp"
              },
              {
                "$ref": "#/channels/userSignedup/messages/inline"
              }
            ]
          }
        },
        "components": {
          "messages": {
            "UserSignedUp": {
              "payload": {
                "$ref": "#/components/schemas/User"
              }
            }
          },
          "schemas": {
            "User": {
              "type": "object",
              "properties": {
                "displayName": {
                  "type": "string"
                },
                "email": {
                  "type": "string"
                }
              }
            }
          }
        }
      }
    `,
  );
});
