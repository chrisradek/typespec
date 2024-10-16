import type {
  AssetEmitter,
  EmittedSourceFile,
  SourceFile,
} from "@typespec/compiler/emitter-framework";

export function getBoilerplateFiles(
  emitter: AssetEmitter<string, Record<string, never>>,
): SourceFileOutput[] {
  return [
    createSourceFile({
      filename: "package.json",
      getContents: getPackageJson,
      emitter,
    }),
    createSourceFile({
      filename: "tsconfig.json",
      getContents: getTSConfig,
      emitter,
    }),
  ];
}

interface CreateSourceFileParams {
  filename: string;
  getContents: () => string;
  emitter: AssetEmitter<string, Record<string, never>>;
}

interface SourceFileOutput {
  filename: string;
  source: SourceFile<string>;
  emitted: EmittedSourceFile;
}

function createSourceFile({
  filename,
  getContents,
  emitter,
}: CreateSourceFileParams): SourceFileOutput {
  const source = emitter.createSourceFile(filename);
  return {
    filename,
    source,
    emitted: {
      path: source.path,
      contents: getContents(),
    },
  };
}

function getPackageJson(): string {
  return JSON.stringify(
    {
      name: "demo-package",
      version: "0.1.0",
      author: "Microsoft Corporation",
      description: "A demo package",
      type: "module",
      main: "index.js",
      scripts: {
        build: "tsc",
      },
      dependencies: {
        "url-template": "^3.1.1",
      },
      devDependencies: {
        "@types/node": "~22.7.5",
        typescript: "~5.6.3",
      },
    },
    null,
    2,
  );
}

function getTSConfig(): string {
  return JSON.stringify(
    {
      compilerOptions: {
        target: "ES2022",
        module: "Node16",
        moduleResolution: "Node16",
      },
    },
    null,
    2,
  );
}
