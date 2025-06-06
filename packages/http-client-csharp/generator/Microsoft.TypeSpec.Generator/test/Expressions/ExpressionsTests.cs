// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using Microsoft.TypeSpec.Generator.Expressions;
using Microsoft.TypeSpec.Generator.Primitives;
using Microsoft.TypeSpec.Generator.Providers;
using Microsoft.TypeSpec.Generator.Statements;
using Microsoft.TypeSpec.Generator.Tests.Common;
using Moq;
using NUnit.Framework;
using static Microsoft.TypeSpec.Generator.Snippets.Snippet;

namespace Microsoft.TypeSpec.Generator.Tests.Expressions
{
    internal class ExpressionsTests
    {
        public ExpressionsTests()
        {
            MockHelpers.LoadMockGenerator();
        }

        [Test]
        public void TestInvokeInstanceMethodExpression()
        {
            // declare the instance method
            var mockTypeProvider = new Mock<TypeProvider>();
            var barMethod = new MethodProvider(
                new MethodSignature(
                    Name: "Bar",
                    Modifiers: MethodSignatureModifiers.Public,
                    ReturnType: typeof(bool),
                    Parameters: [
                        new ParameterProvider("p1", $"p1", new CSharpType(typeof(bool))),
                        new ParameterProvider("p2", $"p2", new CSharpType(typeof(bool))),
                        new ParameterProvider("p3", $"p3", new CSharpType(typeof(bool)))
                    ],
                    Description: null, ReturnDescription: null),
                new MethodBodyStatement[] { Return(True) },
                mockTypeProvider.Object);
            var returnInstanceMethod = Return(new InvokeMethodExpression(null, barMethod.Signature.Name, [Bool(true), Bool(false), Bool(false)]));
            var fooMethod = new MethodProvider(
                new MethodSignature(
                    Name: "Foo",
                    Modifiers: MethodSignatureModifiers.Public,
                    ReturnType: typeof(bool),
                    Parameters: [],
                    Description: null, ReturnDescription: null),
                new MethodBodyStatement[] { returnInstanceMethod },
                mockTypeProvider.Object);

            // Verify the expected behavior
            using var writer = new CodeWriter();
            writer.WriteMethod(barMethod);
            writer.WriteMethod(fooMethod);

            var expectedResult = Helpers.GetExpectedFromFile();
            var test = writer.ToString(false);
            Assert.AreEqual(expectedResult, test);
        }

        [Test]
        public void TestUpdateInvokeInstanceMethodExpression()
        {
            // declare the instance method
            var mockTypeProvider = new Mock<TypeProvider>();
            var returnInstanceMethod = Return(new InvokeMethodExpression(null, "Bar", [Bool(true), Bool(false), Bool(false)]));
            var fooMethod = new MethodProvider(
                new MethodSignature(
                    Name: "Foo",
                    Modifiers: MethodSignatureModifiers.Public,
                    ReturnType: typeof(bool),
                    Parameters: [],
                    Description: null, ReturnDescription: null),
                new MethodBodyStatement[] { returnInstanceMethod },
                mockTypeProvider.Object);

            var returnExpression = returnInstanceMethod as ExpressionStatement;
            Assert.IsNotNull(returnExpression);

            var keyWordExpression = returnExpression!.Expression as KeywordExpression;
            Assert.IsNotNull(keyWordExpression);

            var invokeMethodExpression = keyWordExpression!.Expression as InvokeMethodExpression;
            Assert.IsNotNull(invokeMethodExpression);

            invokeMethodExpression!.Update(
                methodName: "Baz",
                arguments: [Bool(true)]);

            // Verify the expected behavior
            using var writer = new CodeWriter();
            writer.WriteMethod(fooMethod);

            var expectedResult = Helpers.GetExpectedFromFile();
            var test = writer.ToString(false);
            Assert.AreEqual(expectedResult, test);
        }

        [Test]
        public void TestNewInstanceExpression()
        {
            // declare the instance method
            var mockTypeProvider = new Mock<TypeProvider>();
            var newInstanceExpression = new NewInstanceExpression(new CSharpType(typeof(object)), []);
            var variableX = new VariableExpression(typeof(object), "x");
            var xDeclaration = Declare(variableX, newInstanceExpression);
            var fooMethod = new MethodProvider(
                new MethodSignature(
                    Name: "Foo",
                    Modifiers: MethodSignatureModifiers.Public,
                    ReturnType: null,
                    Parameters: [],
                    Description: null, ReturnDescription: null),
                new MethodBodyStatement[] { xDeclaration },
                mockTypeProvider.Object);

            // Verify the expected behavior
            using var writer = new CodeWriter();
            writer.WriteMethod(fooMethod);

            var expectedResult = Helpers.GetExpectedFromFile();
            var test = writer.ToString(false);
            Assert.AreEqual(expectedResult, test);
        }

        [Test]
        public void TestArrayInitializerExpression()
        {
            var mockTypeProvider = new Mock<TypeProvider>();
            var arrayInitializerExpression = new ArrayInitializerExpression([False, True]);
            var newArrayExpression = new NewArrayExpression(typeof(bool), arrayInitializerExpression);
            var variableFoo = new VariableExpression(typeof(bool[]), "foo");
            var fooDeclaration = Declare(variableFoo, newArrayExpression);
            var fooMethod = new MethodProvider(
                new MethodSignature(
                    Name: "Foo",
                    Modifiers: MethodSignatureModifiers.Public,
                    ReturnType: null,
                    Parameters: [],
                    Description: null, ReturnDescription: null),
                new MethodBodyStatement[] { fooDeclaration },
                mockTypeProvider.Object);

            // Verify the expected behavior
            using var writer = new CodeWriter();
            writer.WriteMethod(fooMethod);

            var expectedResult = Helpers.GetExpectedFromFile();
            var test = writer.ToString(false);
            Assert.AreEqual(expectedResult, test);
        }
    }
}
