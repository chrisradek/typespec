// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.Formatting;
using Microsoft.CodeAnalysis.Simplification;
using Microsoft.TypeSpec.Generator.Primitives;
using Microsoft.TypeSpec.Generator.Providers;

namespace Microsoft.TypeSpec.Generator
{
    internal class GeneratedCodeWorkspace
    {
        private const string SharedFolder = "Shared";
        private const string GeneratedFolder = "Generated";
        private const string GeneratedCodeProjectName = "GeneratedCode";
        private const string GeneratedTestFolder = "GeneratedTests";
        private const string NewLine = "\n";

        private static readonly Lazy<IReadOnlyList<MetadataReference>> _assemblyMetadataReferences = new(() => new List<MetadataReference>()
            { MetadataReference.CreateFromFile(typeof(object).Assembly.Location) });
        private static readonly Lazy<WorkspaceMetadataReferenceResolver> _metadataReferenceResolver = new(() => new WorkspaceMetadataReferenceResolver());
        private static Task<Project>? _cachedProject;

        private static readonly string[] _generatedFolders = [GeneratedFolder];
        private static readonly string[] _sharedFolders = [SharedFolder];

        private Project _project;
        private Dictionary<string, string> PlainFiles { get; }

        private GeneratedCodeWorkspace(Project generatedCodeProject)
        {
            _project = generatedCodeProject;
            PlainFiles = new();
        }

        /// <summary>
        /// Creating AdHoc workspace and project takes a while, we'd like to preload this work
        /// to the generator startup time
        /// </summary>
        public static void Initialize()
        {
            _cachedProject = Task.Run(CreateGeneratedCodeProject);
        }

        internal async Task<CSharpCompilation> GetCompilationAsync()
        {
            var compilation = await _project.GetCompilationAsync();
            Debug.Assert(compilation is CSharpCompilation);

            return (CSharpCompilation)compilation;
        }

        public void AddPlainFiles(string name, string content)
        {
            PlainFiles.Add(name, content);
        }

        public async Task AddGeneratedFile(CodeFile codefile)
        {
            var document = _project.AddDocument(codefile.Name, codefile.Content, _generatedFolders);
            await UpdateProject(document);
        }

        public async Task AddInMemoryFile(TypeProvider type)
        {
            var document = _project.AddDocument(type.Name, GetTree(type).GetRoot(), _generatedFolders);
            await UpdateProject(document);
        }

        private async Task UpdateProject(Document document)
        {
            var root = await document.GetSyntaxRootAsync();
            Debug.Assert(root != null);

            root = root.WithAdditionalAnnotations(Simplifier.Annotation);
            document = document.WithSyntaxRoot(root);
            _project = document.Project;
        }

        internal static SyntaxTree GetTree(TypeProvider provider)
        {
            var writer = new TypeProviderWriter(provider);
            var file = writer.Write();
            return CSharpSyntaxTree.ParseText(file.Content, path: Path.Join(provider.RelativeFilePath, provider.Name + ".cs"));
        }

        public async IAsyncEnumerable<(string Name, string Text)> GetGeneratedFilesAsync()
        {
            List<Task<Document>> documents = new List<Task<Document>>();
            foreach (Document document in _project.Documents)
            {
                if (!IsGeneratedDocument(document))
                {
                    continue;
                }

                documents.Add(ProcessDocument(document));
            }
            var docs = await Task.WhenAll(documents);

            foreach (var doc in docs)
            {
                var processed = doc;

                var text = await processed.GetSyntaxTreeAsync();
                yield return (processed.Name, text!.ToString());
            }

            foreach (var (file, content) in PlainFiles)
            {
                yield return (file, content);
            }
        }

        private async Task<Document> ProcessDocument(Document document)
        {
            var syntaxTree = await document.GetSyntaxTreeAsync();
            var compilation = await GetCompilationAsync();
            if (syntaxTree != null)
            {
                var semanticModel = compilation.GetSemanticModel(syntaxTree);
                var modelRemoveRewriter = new MemberRemoverRewriter(_project, semanticModel);
                var root = await syntaxTree.GetRootAsync();
                root = modelRemoveRewriter.Visit(root);

                foreach (var rewriter in CodeModelGenerator.Instance.Rewriters)
                {
                    rewriter.SemanticModel = semanticModel;
                    root = rewriter.Visit(root);
                }
                document = document.WithSyntaxRoot(root);
            }

            document = await Simplifier.ReduceAsync(document);
            return document;
        }

        public static bool IsGeneratedDocument(Document document) => document.Folders.Contains(GeneratedFolder);
        public static bool IsCustomDocument(Document document) => !IsGeneratedDocument(document);
        public static bool IsGeneratedTestDocument(Document document) => document.Folders.Contains(GeneratedTestFolder);

        /// <summary>
        /// Create a new AdHoc workspace using the Roslyn SDK and add a project with all the necessary compilation options.
        /// </summary>
        /// <returns>The created project in the solution.</returns>
        private static Project CreateGeneratedCodeProject()
        {
            var workspace = new AdhocWorkspace();
            var newOptionSet = workspace.Options.WithChangedOption(FormattingOptions.NewLine, LanguageNames.CSharp, NewLine);
            workspace.TryApplyChanges(workspace.CurrentSolution.WithOptions(newOptionSet));
            Project generatedCodeProject = workspace.AddProject(GeneratedCodeProjectName, LanguageNames.CSharp);

            generatedCodeProject = generatedCodeProject
                .AddMetadataReferences(_assemblyMetadataReferences.Value.Concat(CodeModelGenerator.Instance.AdditionalMetadataReferences))
                .WithCompilationOptions(new CSharpCompilationOptions(
                    OutputKind.DynamicallyLinkedLibrary, metadataReferenceResolver: _metadataReferenceResolver.Value, nullableContextOptions: NullableContextOptions.Disable));
            return generatedCodeProject;
        }

        internal static async Task<GeneratedCodeWorkspace> Create()
        {
            // prepare the generated code project
            var projectTask = Interlocked.Exchange(ref _cachedProject, null);
            var project = projectTask != null ? await projectTask : CreateGeneratedCodeProject();

            var outputDirectory = CodeModelGenerator.Instance.Configuration.OutputDirectory;
            var projectDirectory = CodeModelGenerator.Instance.Configuration.ProjectDirectory;
            var generatedDirectory = CodeModelGenerator.Instance.Configuration.ProjectGeneratedDirectory;

            // add all documents except the documents from the generated directory
            if (Path.IsPathRooted(projectDirectory) && Path.IsPathRooted(outputDirectory))
            {
                projectDirectory = Path.GetFullPath(projectDirectory);
                outputDirectory = Path.GetFullPath(outputDirectory);

                Directory.CreateDirectory(projectDirectory);
                Directory.CreateDirectory(outputDirectory);

                project = AddDirectory(project, projectDirectory, skipPredicate: sourceFile => sourceFile.StartsWith(generatedDirectory));
            }

            foreach (var sharedSourceFolder in CodeModelGenerator.Instance.SharedSourceDirectories)
            {
                project = AddDirectory(project, sharedSourceFolder, folders: _sharedFolders);
            }

            project = project.WithParseOptions(new CSharpParseOptions(preprocessorSymbols: new[] { "EXPERIMENTAL" }));

            return new GeneratedCodeWorkspace(project);
        }

        internal static async Task<Compilation?> CreatePreviousContractFromDll(string xmlDocumentationpath, string dllPath)
        {
            var workspace = new AdhocWorkspace();
            Project project = workspace.AddProject("PreviousContract", LanguageNames.CSharp);
            project = project
                .AddMetadataReferences(_assemblyMetadataReferences.Value)
                .WithCompilationOptions(new CSharpCompilationOptions(
                    OutputKind.DynamicallyLinkedLibrary, metadataReferenceResolver: _metadataReferenceResolver.Value, nullableContextOptions: NullableContextOptions.Disable));
            project = project.AddMetadataReference(MetadataReference.CreateFromFile(dllPath, documentation: XmlDocumentationProvider.CreateFromFile(xmlDocumentationpath)));
            return await project.GetCompilationAsync();
        }

        /// <summary>
        /// Add the files in the directory to a project per a given predicate with the folders specified.
        /// </summary>
        /// <param name="project"></param>
        /// <param name="directory"></param>
        /// <param name="skipPredicate"></param>
        /// <param name="folders"></param>
        /// <returns>The <see cref="Project"/> instance with the added directory and files.</returns>
        internal static Project AddDirectory(Project project, string directory, Func<string, bool>? skipPredicate = null, IEnumerable<string>? folders = null)
        {
            foreach (string sourceFile in Directory.GetFiles(directory, "*.cs", SearchOption.AllDirectories))
            {
                if (skipPredicate != null && skipPredicate(sourceFile))
                    continue;

                project = project.AddDocument(sourceFile, File.ReadAllText(sourceFile), folders ?? Array.Empty<string>(), sourceFile).Project;
            }

            return project;
        }

        /// <summary>
        /// This method invokes the postProcessor to do some post processing work
        /// Depending on the configuration, it will either remove + internalize, just internalize or do nothing
        /// </summary>
        public async Task PostProcessAsync()
        {
            var modelFactory = CodeModelGenerator.Instance.OutputLibrary.ModelFactory.Value;
            var postProcessor = new PostProcessor(
                [.. CodeModelGenerator.Instance.TypeFactory.UnionVariantTypesToKeep, .. CodeModelGenerator.Instance.TypesToKeep],
                modelFactoryFullName: $"{modelFactory.Type.Namespace}.{modelFactory.Name}");
            switch (Configuration.UnreferencedTypesHandling)
            {
                case Configuration.UnreferencedTypesHandlingOption.KeepAll:
                    break;
                case Configuration.UnreferencedTypesHandlingOption.Internalize:
                    _project = await postProcessor.InternalizeAsync(_project);
                    break;
                case Configuration.UnreferencedTypesHandlingOption.RemoveOrInternalize:
                    _project = await postProcessor.InternalizeAsync(_project);
                    _project = await postProcessor.RemoveAsync(_project);
                    break;
            }
        }
    }
}
