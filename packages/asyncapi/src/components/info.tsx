import * as aj from "@alloy-js/json";
import { getService } from "@typespec/compiler";
import { useTsp } from "@typespec/emitter-framework";
import { getInfo } from "../decorators.js";
import { AsyncService } from "../service.js";

export interface ServiceInfoProps {
  service: AsyncService;
}

export function ServiceInfo(props: ServiceInfoProps) {
  const { program } = useTsp();
  const info = getInfo(program, props.service.namespace);

  const jsValue: Record<string, any> = {};

  const title = info?.title ?? getService(program, props.service.namespace)?.title;
  if (title) jsValue.title = title;

  const version = info?.version;
  if (version) jsValue.version = version;

  return <aj.JsonObjectProperty name="info" jsValue={jsValue} />;
}
