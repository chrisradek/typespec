// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com._specs_.azure.example.basic;

import com._specs_.azure.example.basic.implementation.AzureExampleClientImpl;
import com._specs_.azure.example.basic.models.ActionRequest;
import com._specs_.azure.example.basic.models.ActionResponse;
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

/**
 * Initializes a new instance of the synchronous AzureExampleClient type.
 */
@ServiceClient(builder = AzureExampleClientBuilder.class)
public final class AzureExampleClient {
    @Generated
    private final AzureExampleClientImpl serviceClient;

    /**
     * Initializes an instance of AzureExampleClient class.
     * 
     * @param serviceClient the service client implementation.
     */
    @Generated
    AzureExampleClient(AzureExampleClientImpl serviceClient) {
        this.serviceClient = serviceClient;
    }

    /**
     * The basicAction operation.
     * <p><strong>Request Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     stringProperty: String (Required)
     *     modelProperty (Optional): {
     *         int32Property: Integer (Optional)
     *         float32Property: Double (Optional)
     *         enumProperty: String(EnumValue1) (Optional)
     *     }
     *     arrayProperty (Optional): [
     *         String (Optional)
     *     ]
     *     recordProperty (Optional): {
     *         String: String (Required)
     *     }
     * }
     * }
     * </pre>
     * 
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     stringProperty: String (Required)
     *     modelProperty (Optional): {
     *         int32Property: Integer (Optional)
     *         float32Property: Double (Optional)
     *         enumProperty: String(EnumValue1) (Optional)
     *     }
     *     arrayProperty (Optional): [
     *         String (Optional)
     *     ]
     *     recordProperty (Optional): {
     *         String: String (Required)
     *     }
     * }
     * }
     * </pre>
     * 
     * @param queryParam The queryParam parameter.
     * @param headerParam The headerParam parameter.
     * @param body The body parameter.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return the response body along with {@link Response}.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<BinaryData> basicActionWithResponse(String queryParam, String headerParam, BinaryData body,
        RequestOptions requestOptions) {
        return this.serviceClient.basicActionWithResponse(queryParam, headerParam, body, requestOptions);
    }

    /**
     * The basicAction operation.
     * 
     * @param queryParam The queryParam parameter.
     * @param headerParam The headerParam parameter.
     * @param body The body parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @Generated
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ActionResponse basicAction(String queryParam, String headerParam, ActionRequest body) {
        // Generated convenience method for basicActionWithResponse
        RequestOptions requestOptions = new RequestOptions();
        return basicActionWithResponse(queryParam, headerParam, BinaryData.fromObject(body), requestOptions).getValue()
            .toObject(ActionResponse.class);
    }
}