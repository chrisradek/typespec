// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.union.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;
import tsptest.union.UnionServiceVersion;

/**
 * Initializes a new instance of the UnionClient type.
 */
public final class UnionClientImpl {
    /**
     */
    private final String endpoint;

    /**
     * Gets.
     * 
     * @return the endpoint value.
     */
    public String getEndpoint() {
        return this.endpoint;
    }

    /**
     * Service version.
     */
    private final UnionServiceVersion serviceVersion;

    /**
     * Gets Service version.
     * 
     * @return the serviceVersion value.
     */
    public UnionServiceVersion getServiceVersion() {
        return this.serviceVersion;
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
     * The UnionFlattenOpsImpl object to access its operations.
     */
    private final UnionFlattenOpsImpl unionFlattenOps;

    /**
     * Gets the UnionFlattenOpsImpl object to access its operations.
     * 
     * @return the UnionFlattenOpsImpl object.
     */
    public UnionFlattenOpsImpl getUnionFlattenOps() {
        return this.unionFlattenOps;
    }

    /**
     * Initializes an instance of UnionClient client.
     * 
     * @param endpoint
     * @param serviceVersion Service version.
     */
    public UnionClientImpl(String endpoint, UnionServiceVersion serviceVersion) {
        this(new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy()).build(),
            JacksonAdapter.createDefaultSerializerAdapter(), endpoint, serviceVersion);
    }

    /**
     * Initializes an instance of UnionClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param endpoint
     * @param serviceVersion Service version.
     */
    public UnionClientImpl(HttpPipeline httpPipeline, String endpoint, UnionServiceVersion serviceVersion) {
        this(httpPipeline, JacksonAdapter.createDefaultSerializerAdapter(), endpoint, serviceVersion);
    }

    /**
     * Initializes an instance of UnionClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param serializerAdapter The serializer to serialize an object into a string.
     * @param endpoint
     * @param serviceVersion Service version.
     */
    public UnionClientImpl(HttpPipeline httpPipeline, SerializerAdapter serializerAdapter, String endpoint,
        UnionServiceVersion serviceVersion) {
        this.httpPipeline = httpPipeline;
        this.serializerAdapter = serializerAdapter;
        this.endpoint = endpoint;
        this.serviceVersion = serviceVersion;
        this.unionFlattenOps = new UnionFlattenOpsImpl(this);
    }
}
