// <auto-generated/>

#nullable disable

using System.ClientModel;
using System.ClientModel.Primitives;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace Parameters.Spread._Alias
{
    public partial class Alias
    {
        protected Alias() => throw null;

        public ClientPipeline Pipeline => throw null;

        public virtual ClientResult SpreadAsRequestBody(BinaryContent content, RequestOptions options = null) => throw null;

        public virtual Task<ClientResult> SpreadAsRequestBodyAsync(BinaryContent content, RequestOptions options = null) => throw null;

        public virtual ClientResult SpreadAsRequestBody(string name, CancellationToken cancellationToken = default) => throw null;

        public virtual Task<ClientResult> SpreadAsRequestBodyAsync(string name, CancellationToken cancellationToken = default) => throw null;

        public virtual ClientResult SpreadParameterWithInnerModel(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual Task<ClientResult> SpreadParameterWithInnerModelAsync(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual ClientResult SpreadParameterWithInnerModel(string id, string xMsTestHeader, string name, CancellationToken cancellationToken = default) => throw null;

        public virtual Task<ClientResult> SpreadParameterWithInnerModelAsync(string id, string xMsTestHeader, string name, CancellationToken cancellationToken = default) => throw null;

        public virtual ClientResult SpreadAsRequestParameter(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual Task<ClientResult> SpreadAsRequestParameterAsync(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual ClientResult SpreadAsRequestParameter(string id, string xMsTestHeader, string name, CancellationToken cancellationToken = default) => throw null;

        public virtual Task<ClientResult> SpreadAsRequestParameterAsync(string id, string xMsTestHeader, string name, CancellationToken cancellationToken = default) => throw null;

        public virtual ClientResult SpreadWithMultipleParameters(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual Task<ClientResult> SpreadWithMultipleParametersAsync(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual ClientResult SpreadWithMultipleParameters(string id, string xMsTestHeader, string requiredString, IEnumerable<int> requiredIntList, int? optionalInt = default, IEnumerable<string> optionalStringList = default, CancellationToken cancellationToken = default) => throw null;

        public virtual Task<ClientResult> SpreadWithMultipleParametersAsync(string id, string xMsTestHeader, string requiredString, IEnumerable<int> requiredIntList, int? optionalInt = default, IEnumerable<string> optionalStringList = default, CancellationToken cancellationToken = default) => throw null;

        public virtual ClientResult SpreadParameterWithInnerAlias(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual Task<ClientResult> SpreadParameterWithInnerAliasAsync(string id, string xMsTestHeader, BinaryContent content, RequestOptions options = null) => throw null;

        public virtual ClientResult SpreadParameterWithInnerAlias(string id, string xMsTestHeader, string name, int age, CancellationToken cancellationToken = default) => throw null;

        public virtual Task<ClientResult> SpreadParameterWithInnerAliasAsync(string id, string xMsTestHeader, string name, int age, CancellationToken cancellationToken = default) => throw null;
    }
}
