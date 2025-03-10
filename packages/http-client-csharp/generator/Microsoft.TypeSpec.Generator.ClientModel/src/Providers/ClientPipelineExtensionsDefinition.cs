// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.TypeSpec.Generator.ClientModel.Snippets;
using Microsoft.TypeSpec.Generator.Expressions;
using Microsoft.TypeSpec.Generator.Primitives;
using Microsoft.TypeSpec.Generator.Providers;
using Microsoft.TypeSpec.Generator.Statements;
using static Microsoft.TypeSpec.Generator.Snippets.Snippet;

namespace Microsoft.TypeSpec.Generator.ClientModel.Providers
{
    internal class ClientPipelineExtensionsDefinition : TypeProvider
    {
        private ParameterProvider _pipelineParam;
        private ParameterProvider _messageParam;
        private ParameterProvider _requestOptionsParam;
        private ClientPipelineApi _pipeline;
        private HttpMessageApi _message;
        private HttpRequestOptionsApi _options;

        public ClientPipelineExtensionsDefinition()
        {
            _pipelineParam = new ParameterProvider("pipeline", FormattableStringHelpers.Empty, ScmCodeModelPlugin.Instance.TypeFactory.ClientPipelineApi.ClientPipelineType);
            _messageParam = new ParameterProvider("message", FormattableStringHelpers.Empty, ScmCodeModelPlugin.Instance.TypeFactory.HttpMessageApi.HttpMessageType);
            _requestOptionsParam = new ParameterProvider(ScmCodeModelPlugin.Instance.TypeFactory.HttpRequestOptionsApi.ParameterName, FormattableStringHelpers.Empty, ScmCodeModelPlugin.Instance.TypeFactory.HttpRequestOptionsApi.HttpRequestOptionsType);
            _pipeline = _pipelineParam.ToApi<ClientPipelineApi>();
            _message = _messageParam.ToApi<HttpMessageApi>();
            _options = _requestOptionsParam.ToApi<HttpRequestOptionsApi>();
        }

        protected override TypeSignatureModifiers BuildDeclarationModifiers()
        {
            return TypeSignatureModifiers.Internal | TypeSignatureModifiers.Static;
        }

        protected override string BuildRelativeFilePath() => Path.Combine("src", "Generated", "Internal", $"{Name}.cs");

        protected override string BuildName() => "ClientPipelineExtensions";

        protected override MethodProvider[] BuildMethods()
        {
            return
            [
                BuildProcessMessageAsync(),
                BuildProcessMessage(),
                ProcessHeadAsBoolMessageAsync(),
                ProcessHeadAsBoolMessage()
            ];
        }

        private MethodProvider ProcessHeadAsBoolMessage()
        {
            MethodSignature signature = GetProcessHeadAsBoolMessageSignature(false);
            var responseVariable = new VariableExpression(ScmCodeModelPlugin.Instance.TypeFactory.HttpResponseApi.HttpResponseType, "response");
            var response = responseVariable.ToApi<HttpResponseApi>();
            return new MethodProvider(signature, new MethodBodyStatement[]
            {
                new DeclarationExpression(responseVariable, false).Assign(_pipeline.ProcessMessage(_message, _options, false)).Terminate(),
                GetProcessHeadAsBoolMessageBody(response)
            }, this);
        }

        private MethodProvider ProcessHeadAsBoolMessageAsync()
        {
            MethodSignature signature = GetProcessHeadAsBoolMessageSignature(true);
            var responseVariable = new VariableExpression(ScmCodeModelPlugin.Instance.TypeFactory.HttpResponseApi.HttpResponseType, "response");
            var response = responseVariable.ToApi<HttpResponseApi>();
            return new MethodProvider(signature, new MethodBodyStatement[]
            {
                new DeclarationExpression(responseVariable, false).Assign(_pipeline.ProcessMessage(_message, _options, true)).Terminate(),
                GetProcessHeadAsBoolMessageBody(response)
            }, this);
        }

        private MethodBodyStatement GetProcessHeadAsBoolMessageBody(HttpResponseApi response)
        {
            return new MethodBodyStatement[]
            {
                new SwitchStatement(new MemberExpression(response, "Status"),
                [
                    new SwitchCaseStatement(ValueExpression.Empty.GreaterThanOrEqual(Literal(200)).AndExpr(ValueExpression.Empty.LessThan(Literal(300))), new MethodBodyStatement[]
                    {
                        Return(ScmCodeModelPlugin.Instance.TypeFactory.ClientResponseApi.ToExpression().FromValue<bool>(True, response))
                    }),
                    new SwitchCaseStatement(ValueExpression.Empty.GreaterThanOrEqual(Literal(400)).AndExpr(ValueExpression.Empty.LessThan(Literal(500))), new MethodBodyStatement[]
                    {
                        Return(ScmCodeModelPlugin.Instance.TypeFactory.ClientResponseApi.ToExpression().FromValue<bool>(False, response))
                    }),
                    new SwitchCaseStatement(Array.Empty<ValueExpression>(), new MethodBodyStatement[]
                    {
                        Return(new NewInstanceExpression(ErrorResultSnippets.ErrorResultType.MakeGenericType([typeof(bool)]), [response, new NewInstanceExpression(ScmCodeModelPlugin.Instance.TypeFactory.ClientResponseApi.ClientResponseExceptionType, [response])]))
                    })
                ]),
            };
        }

        private MethodSignature GetProcessHeadAsBoolMessageSignature(bool isAsync)
        {
            var modifiers = MethodSignatureModifiers.Public | MethodSignatureModifiers.Static | MethodSignatureModifiers.Extension;
            if (isAsync)
            {
                modifiers |= MethodSignatureModifiers.Async;
            }
            var clientResultType = new CSharpType(ScmCodeModelPlugin.Instance.TypeFactory.ClientResponseApi.ClientResponseOfTType.FrameworkType, typeof(bool));
            return new MethodSignature(
                isAsync ? "ProcessHeadAsBoolMessageAsync" : "ProcessHeadAsBoolMessage",
                null,
                modifiers,
                isAsync ? new CSharpType(typeof(ValueTask<>), clientResultType) : clientResultType,
                null,
                [_pipelineParam, _messageParam, _requestOptionsParam]);
        }

        private MethodProvider BuildProcessMessage()
        {
            MethodSignature signature = GetProcessMessageSignature(false);
            return new MethodProvider(signature, _pipeline.ProcessMessage(_message, _options), this);
        }

        private MethodSignature GetProcessMessageSignature(bool isAsync)
        {
            var modifiers = MethodSignatureModifiers.Public | MethodSignatureModifiers.Static | MethodSignatureModifiers.Extension;
            if (isAsync)
            {
                modifiers |= MethodSignatureModifiers.Async;
            }
            return new MethodSignature(
                isAsync ? "ProcessMessageAsync" : "ProcessMessage",
                null,
                modifiers,
                isAsync ? new CSharpType(typeof(ValueTask<>), ScmCodeModelPlugin.Instance.TypeFactory.HttpResponseApi.HttpResponseType) : ScmCodeModelPlugin.Instance.TypeFactory.HttpResponseApi.HttpResponseType,
                null,
                [_pipelineParam, _messageParam, _requestOptionsParam]);
        }

        private MethodProvider BuildProcessMessageAsync()
        {
            MethodSignature signature = GetProcessMessageSignature(true);
            return new MethodProvider(signature, _pipeline.ProcessMessageAsync(_message, _options), this);
        }
    }
}
