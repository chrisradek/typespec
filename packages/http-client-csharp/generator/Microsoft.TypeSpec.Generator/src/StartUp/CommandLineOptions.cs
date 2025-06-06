// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using System.Diagnostics.CodeAnalysis;
using CommandLine;

namespace Microsoft.TypeSpec.Generator
{
    /// <summary>
    /// Options for the command line when running the generator.
    /// </summary>
    internal class CommandLineOptions
    {
        private const string OutputDirectoryOptionName = "DIRECTORY";
        private const string ShouldDebugOptionName = "debug";
        private const string GeneratorNameOptionName = "generatorName";
        private const string NewProjectOptionName = "new-project";
        private const string CmdLineOutputDirectoryOptionHelpText = "The path to the directory containing the input files to the generator including the code model file and the configuration file for the generator.";
        private const string CmdLineDebugOptionHelpText = "Attempt to attach the debugger on execute.";
        private const string CmdLineGeneratorOptionHelpText = "The name of the generator to execute.";
        private const string CmdLineNewProjectOptionHelpText = "Indicates if the generator should create the project files such as csproj, sln, etc.";

        /// <summary>
        /// The command line option to specify the path to the directory containing the input files to the generator.
        /// </summary>
        [Value(0, MetaName = OutputDirectoryOptionName, Default = null, Required = true, HelpText = CmdLineOutputDirectoryOptionHelpText)]
        [NotNull]
        public string? OutputDirectory { get; set; }

        [Option(longName: ShouldDebugOptionName, Required = false, Default = false, Hidden = true, HelpText = CmdLineDebugOptionHelpText)]
        public bool ShouldDebug { get; set; }

        [Option(longName: GeneratorNameOptionName, shortName: 'g', Required = true, Hidden = false, HelpText = CmdLineGeneratorOptionHelpText)]
        public string? GeneratorName { get; set; }

        [Option(longName: NewProjectOptionName, shortName: 'n', Required = false, Default = false, Hidden = false, HelpText = CmdLineNewProjectOptionHelpText)]
        public bool IsNewProject { get; set; }
    }
}
