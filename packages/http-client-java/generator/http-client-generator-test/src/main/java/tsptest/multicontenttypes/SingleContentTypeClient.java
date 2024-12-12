// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.multicontenttypes;

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
import com.azure.core.util.BinaryData;
import tsptest.multicontenttypes.implementation.SingleContentTypesImpl;

/**
 * Initializes a new instance of the synchronous MultiContentTypesClient type.
 */
@ServiceClient(builder = MultiContentTypesClientBuilder.class)
public final class SingleContentTypeClient {
    @Generated
    private final SingleContentTypesImpl serviceClient;

    /**
     * Initializes an instance of SingleContentTypeClient class.
     * 
     * @param serviceClient the service client implementation.
     */
    @Generated
    SingleContentTypeClient(SingleContentTypesImpl serviceClient) {
        this.serviceClient = serviceClient;
    }

    /**
     * response is binary.
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * BinaryData
     * }
     * </pre>
     * 
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return the response body along with {@link Response}.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<BinaryData> downloadImageForSingleContentTypeWithResponse(RequestOptions requestOptions) {
        return this.serviceClient.downloadImageForSingleContentTypeWithResponse(requestOptions);
    }

    /**
     * request is binary.
     * <p><strong>Request Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * BinaryData
     * }
     * </pre>
     * 
     * @param data The data parameter.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return the {@link Response}.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> uploadImageForSingleContentTypeWithResponse(BinaryData data, RequestOptions requestOptions) {
        return this.serviceClient.uploadImageForSingleContentTypeWithResponse(data, requestOptions);
    }

    /**
     * response is binary.
     * 
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public BinaryData downloadImageForSingleContentType() {
        // Generated convenience method for downloadImageForSingleContentTypeWithResponse
        RequestOptions requestOptions = new RequestOptions();
        return downloadImageForSingleContentTypeWithResponse(requestOptions).getValue();
    }

    /**
     * request is binary.
     * 
     * @param data The data parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void uploadImageForSingleContentType(BinaryData data) {
        // Generated convenience method for uploadImageForSingleContentTypeWithResponse
        RequestOptions requestOptions = new RequestOptions();
        uploadImageForSingleContentTypeWithResponse(data, requestOptions).getValue();
    }
}