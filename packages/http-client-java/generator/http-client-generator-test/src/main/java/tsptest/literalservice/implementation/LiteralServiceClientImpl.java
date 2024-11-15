// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.literalservice.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;

/**
 * Initializes a new instance of the LiteralServiceClient type.
 */
public final class LiteralServiceClientImpl {
    /**
     * Service host.
     */
    private final String endpoint;

    /**
     * Gets Service host.
     * 
     * @return the endpoint value.
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    /**
     * The HTTP pipeline to send requests through.
     */
    private final HttpPipeline httpPipeline;

    /**
     * Gets The HTTP pipeline to send requests through.
     * 
     * @return the httpPipeline value.
     */
    public HttpPipeline getHttpPipeline() {
        return this.httpPipeline;
    }

    /**
     * The serializer to serialize an object into a string.
     */
    private final SerializerAdapter serializerAdapter;

    /**
     * Gets The serializer to serialize an object into a string.
     * 
     * @return the serializerAdapter value.
     */
    public SerializerAdapter getSerializerAdapter() {
        return this.serializerAdapter;
    }

    /**
     * The LiteralOpsImpl object to access its operations.
     */
    private final LiteralOpsImpl literalOps;

    /**
     * Gets the LiteralOpsImpl object to access its operations.
     * 
     * @return the LiteralOpsImpl object.
     */
    public LiteralOpsImpl getLiteralOps() {
        return this.literalOps;
    }

    /**
     * Initializes an instance of LiteralServiceClient client.
     * 
     * @param endpoint Service host.
     */
    public LiteralServiceClientImpl(String endpoint) {
        this(new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy()).build(),
            JacksonAdapter.createDefaultSerializerAdapter(), endpoint);
    }

    /**
     * Initializes an instance of LiteralServiceClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param endpoint Service host.
     */
    public LiteralServiceClientImpl(HttpPipeline httpPipeline, String endpoint) {
        this(httpPipeline, JacksonAdapter.createDefaultSerializerAdapter(), endpoint);
    }

    /**
     * Initializes an instance of LiteralServiceClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param serializerAdapter The serializer to serialize an object into a string.
     * @param endpoint Service host.
     */
    public LiteralServiceClientImpl(HttpPipeline httpPipeline, SerializerAdapter serializerAdapter, String endpoint) {
        this.httpPipeline = httpPipeline;
        this.serializerAdapter = serializerAdapter;
        this.endpoint = endpoint;
        this.literalOps = new LiteralOpsImpl(this);
    }
}