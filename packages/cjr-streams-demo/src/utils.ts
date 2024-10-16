import type { Program } from "@typespec/compiler";

export async function ensureCleanDirectory(program: Program, targetPath: string) {
  try {
    await program.host.stat(targetPath);
    await program.host.rm(targetPath, { recursive: true });
  } catch {}

  await program.host.mkdirp(targetPath);
}
