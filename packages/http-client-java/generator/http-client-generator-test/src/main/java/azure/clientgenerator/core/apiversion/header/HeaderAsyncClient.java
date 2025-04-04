// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package azure.clientgenerator.core.apiversion.header;

import azure.clientgenerator.core.apiversion.header.implementation.HeaderClientImpl;
import com.azure.core.annotation.Generated;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.exception.ClientAuthenticationException;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.exception.ResourceModifiedException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.rest.RequestOptions;
import com.azure.core.http.rest.Response;
import com.azure.core.util.FluxUtil;
import reactor.core.publisher.Mono;

/**
 * Initializes a new instance of the asynchronous HeaderClient type.
 */
@ServiceClient(builder = HeaderClientBuilder.class, isAsync = true)
public final class HeaderAsyncClient {
    @Generated
    private final HeaderClientImpl serviceClient;

    /**
     * Initializes an instance of HeaderAsyncClient class.
     * 
     * @param serviceClient the service client implementation.
     */
    @Generated
    HeaderAsyncClient(HeaderClientImpl serviceClient) {
        this.serviceClient = serviceClient;
    }

    /**
     * Header api version parameter.
     * 
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return the {@link Response} on successful completion of {@link Mono}.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> headerApiVersionWithResponse(RequestOptions requestOptions) {
        return this.serviceClient.headerApiVersionWithResponseAsync(requestOptions);
    }

    /**
     * Header api version parameter.
     * 
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return A {@link Mono} that completes when a successful response is received.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> headerApiVersion() {
        // Generated convenience method for headerApiVersionWithResponse
        RequestOptions requestOptions = new RequestOptions();
        return headerApiVersionWithResponse(requestOptions).flatMap(FluxUtil::toMono);
    }
}
