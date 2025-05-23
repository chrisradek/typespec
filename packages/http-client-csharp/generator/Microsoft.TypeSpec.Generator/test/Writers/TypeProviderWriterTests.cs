// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.TypeSpec.Generator.Input;
using Microsoft.TypeSpec.Generator.Primitives;
using Microsoft.TypeSpec.Generator.Providers;
using Microsoft.TypeSpec.Generator.Tests.Common;
using NUnit.Framework;

namespace Microsoft.TypeSpec.Generator.Tests.Writers
{
    internal class TypeProviderWriterTests
    {
        public TypeProviderWriterTests()
        {
            MockHelpers.LoadMockGenerator();
        }

        // Tests that the Write method is successfully overridden.
        [Test]
        public void Write_Override()
        {
            var writer = new MockExpressionTypeProviderWriter(TestTypeProvider.Empty);
            Assert.That(writer.Write, Throws.Exception.TypeOf<NotImplementedException>());
        }

        private class MockExpressionTypeProviderWriter : TypeProviderWriter
        {
            public MockExpressionTypeProviderWriter(TypeProvider provider) : base(provider) { }

            public override CodeFile Write()
            {
                throw new NotImplementedException();
            }
        }

        [Test]
        public void TypeProviderWriter_WriteModel()
        {
            var properties = new List<InputModelProperty> { RequiredStringProperty, RequiredIntProperty };
            var inputModel = InputFactory.Model("TestModel", properties: properties);
            MockHelpers.LoadMockGenerator(inputModelTypes: [inputModel]);

            var modelProvider = new ModelProvider(inputModel);
            var codeFile = new TypeProviderWriter(modelProvider).Write();
            var result = codeFile.Content;

            var expected = Helpers.GetExpectedFromFile();

            Assert.AreEqual(expected, result);
        }

        [Test]
        public void TypeProviderWriter_WriteModelAsStruct()
        {
            var properties = new List<InputModelProperty> { RequiredStringProperty, RequiredIntProperty };
            var inputModel = InputFactory.Model("TestModel", properties: properties, modelAsStruct: true);
            MockHelpers.LoadMockGenerator(inputModelTypes: [inputModel]);

            var modelProvider = new ModelProvider(inputModel);
            var codeFile = new TypeProviderWriter(modelProvider).Write();
            var result = codeFile.Content;

            var expected = Helpers.GetExpectedFromFile();

            Assert.AreEqual(expected, result);
        }

        // common usages definitions
        internal static readonly InputModelProperty RequiredStringProperty = InputFactory.Property("requiredString", InputPrimitiveType.String, isRequired: true);

        internal static readonly InputModelProperty RequiredIntProperty = InputFactory.Property("requiredInt", InputPrimitiveType.Int32, isRequired: true);
    }
}
