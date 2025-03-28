// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.armstreamstyleserialization.models;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import tsptest.armstreamstyleserialization.fluent.models.FunctionInner;

/**
 * Resource collection API of Functions.
 */
public interface Functions {
    /**
     * The createFunction operation.
     * 
     * @param function The function parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response body along with {@link Response}.
     */
    Response<Function> createFunctionWithResponse(FunctionInner function, Context context);

    /**
     * The createFunction operation.
     * 
     * @param function The function parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    Function createFunction(FunctionInner function);
}
