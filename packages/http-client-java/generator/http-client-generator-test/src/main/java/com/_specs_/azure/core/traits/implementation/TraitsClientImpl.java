// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com._specs_.azure.core.traits.implementation;

import com._specs_.azure.core.traits.TraitsServiceVersion;
import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.HeaderParam;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Post;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.exception.ClientAuthenticationException;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.exception.ResourceModifiedException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.HttpHeaderName;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.http.rest.RequestOptions;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.BinaryData;
import com.azure.core.util.Context;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.DateTimeRfc1123;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;
import java.time.OffsetDateTime;
import reactor.core.publisher.Mono;

/**
 * Initializes a new instance of the TraitsClient type.
 */
public final class TraitsClientImpl {
    /**
     * The proxy service used to perform REST calls.
     */
    private final TraitsClientService service;

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
     * Service version.
     */
    private final TraitsServiceVersion serviceVersion;

    /**
     * Gets Service version.
     * 
     * @return the serviceVersion value.
     */
    public TraitsServiceVersion getServiceVersion() {
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
     * Initializes an instance of TraitsClient client.
     * 
     * @param endpoint Service host.
     * @param serviceVersion Service version.
     */
    public TraitsClientImpl(String endpoint, TraitsServiceVersion serviceVersion) {
        this(new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy()).build(),
            JacksonAdapter.createDefaultSerializerAdapter(), endpoint, serviceVersion);
    }

    /**
     * Initializes an instance of TraitsClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param endpoint Service host.
     * @param serviceVersion Service version.
     */
    public TraitsClientImpl(HttpPipeline httpPipeline, String endpoint, TraitsServiceVersion serviceVersion) {
        this(httpPipeline, JacksonAdapter.createDefaultSerializerAdapter(), endpoint, serviceVersion);
    }

    /**
     * Initializes an instance of TraitsClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param serializerAdapter The serializer to serialize an object into a string.
     * @param endpoint Service host.
     * @param serviceVersion Service version.
     */
    public TraitsClientImpl(HttpPipeline httpPipeline, SerializerAdapter serializerAdapter, String endpoint,
        TraitsServiceVersion serviceVersion) {
        this.httpPipeline = httpPipeline;
        this.serializerAdapter = serializerAdapter;
        this.endpoint = endpoint;
        this.serviceVersion = serviceVersion;
        this.service = RestProxy.create(TraitsClientService.class, this.httpPipeline, this.getSerializerAdapter());
    }

    /**
     * The interface defining all the services for TraitsClient to be used by the proxy service to perform REST calls.
     */
    @Host("{endpoint}")
    @ServiceInterface(name = "TraitsClient")
    public interface TraitsClientService {
        @Get("/azure/core/traits/user/{id}")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(value = ClientAuthenticationException.class, code = { 401 })
        @UnexpectedResponseExceptionType(value = ResourceNotFoundException.class, code = { 404 })
        @UnexpectedResponseExceptionType(value = ResourceModifiedException.class, code = { 409 })
        @UnexpectedResponseExceptionType(HttpResponseException.class)
        Mono<Response<BinaryData>> smokeTest(@HostParam("endpoint") String endpoint,
            @QueryParam("api-version") String apiVersion, @PathParam("id") int id, @HeaderParam("foo") String foo,
            @HeaderParam("Accept") String accept, RequestOptions requestOptions, Context context);

        @Get("/azure/core/traits/user/{id}")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(value = ClientAuthenticationException.class, code = { 401 })
        @UnexpectedResponseExceptionType(value = ResourceNotFoundException.class, code = { 404 })
        @UnexpectedResponseExceptionType(value = ResourceModifiedException.class, code = { 409 })
        @UnexpectedResponseExceptionType(HttpResponseException.class)
        Response<BinaryData> smokeTestSync(@HostParam("endpoint") String endpoint,
            @QueryParam("api-version") String apiVersion, @PathParam("id") int id, @HeaderParam("foo") String foo,
            @HeaderParam("Accept") String accept, RequestOptions requestOptions, Context context);

        @Post("/azure/core/traits/user/{id}:repeatableAction")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(value = ClientAuthenticationException.class, code = { 401 })
        @UnexpectedResponseExceptionType(value = ResourceNotFoundException.class, code = { 404 })
        @UnexpectedResponseExceptionType(value = ResourceModifiedException.class, code = { 409 })
        @UnexpectedResponseExceptionType(HttpResponseException.class)
        Mono<Response<BinaryData>> repeatableAction(@HostParam("endpoint") String endpoint,
            @QueryParam("api-version") String apiVersion, @PathParam("id") int id,
            @HeaderParam("Content-Type") String contentType, @HeaderParam("Accept") String accept,
            @BodyParam("application/json") BinaryData body, RequestOptions requestOptions, Context context);

        @Post("/azure/core/traits/user/{id}:repeatableAction")
        @ExpectedResponses({ 200 })
        @UnexpectedResponseExceptionType(value = ClientAuthenticationException.class, code = { 401 })
        @UnexpectedResponseExceptionType(value = ResourceNotFoundException.class, code = { 404 })
        @UnexpectedResponseExceptionType(value = ResourceModifiedException.class, code = { 409 })
        @UnexpectedResponseExceptionType(HttpResponseException.class)
        Response<BinaryData> repeatableActionSync(@HostParam("endpoint") String endpoint,
            @QueryParam("api-version") String apiVersion, @PathParam("id") int id,
            @HeaderParam("Content-Type") String contentType, @HeaderParam("Accept") String accept,
            @BodyParam("application/json") BinaryData body, RequestOptions requestOptions, Context context);
    }

    /**
     * Get a resource, sending and receiving headers.
     * <p><strong>Header Parameters</strong></p>
     * <table border="1">
     * <caption>Header Parameters</caption>
     * <tr><th>Name</th><th>Type</th><th>Required</th><th>Description</th></tr>
     * <tr><td>If-Match</td><td>String</td><td>No</td><td>The request should only proceed if an entity matches this
     * string.</td></tr>
     * <tr><td>If-None-Match</td><td>String</td><td>No</td><td>The request should only proceed if no entity matches this
     * string.</td></tr>
     * <tr><td>If-Unmodified-Since</td><td>OffsetDateTime</td><td>No</td><td>The request should only proceed if the
     * entity was not modified after this time.</td></tr>
     * <tr><td>If-Modified-Since</td><td>OffsetDateTime</td><td>No</td><td>The request should only proceed if the entity
     * was modified after this time.</td></tr>
     * </table>
     * You can add these to a request with {@link RequestOptions#addHeader}
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     id: int (Required)
     *     name: String (Optional)
     * }
     * }
     * </pre>
     * 
     * @param id The user's id.
     * @param foo header in request.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return a resource, sending and receiving headers along with {@link Response} on successful completion of
     * {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<BinaryData>> smokeTestWithResponseAsync(int id, String foo, RequestOptions requestOptions) {
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.smokeTest(this.getEndpoint(),
            this.getServiceVersion().getVersion(), id, foo, accept, requestOptions, context));
    }

    /**
     * Get a resource, sending and receiving headers.
     * <p><strong>Header Parameters</strong></p>
     * <table border="1">
     * <caption>Header Parameters</caption>
     * <tr><th>Name</th><th>Type</th><th>Required</th><th>Description</th></tr>
     * <tr><td>If-Match</td><td>String</td><td>No</td><td>The request should only proceed if an entity matches this
     * string.</td></tr>
     * <tr><td>If-None-Match</td><td>String</td><td>No</td><td>The request should only proceed if no entity matches this
     * string.</td></tr>
     * <tr><td>If-Unmodified-Since</td><td>OffsetDateTime</td><td>No</td><td>The request should only proceed if the
     * entity was not modified after this time.</td></tr>
     * <tr><td>If-Modified-Since</td><td>OffsetDateTime</td><td>No</td><td>The request should only proceed if the entity
     * was modified after this time.</td></tr>
     * </table>
     * You can add these to a request with {@link RequestOptions#addHeader}
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     id: int (Required)
     *     name: String (Optional)
     * }
     * }
     * </pre>
     * 
     * @param id The user's id.
     * @param foo header in request.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return a resource, sending and receiving headers along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<BinaryData> smokeTestWithResponse(int id, String foo, RequestOptions requestOptions) {
        final String accept = "application/json";
        return service.smokeTestSync(this.getEndpoint(), this.getServiceVersion().getVersion(), id, foo, accept,
            requestOptions, Context.NONE);
    }

    /**
     * Test for repeatable requests.
     * <p><strong>Header Parameters</strong></p>
     * <table border="1">
     * <caption>Header Parameters</caption>
     * <tr><th>Name</th><th>Type</th><th>Required</th><th>Description</th></tr>
     * <tr><td>repeatability-request-id</td><td>String</td><td>No</td><td>Repeatability request ID header</td></tr>
     * <tr><td>repeatability-first-sent</td><td>String</td><td>No</td><td>Repeatability first sent header as
     * HTTP-date</td></tr>
     * </table>
     * You can add these to a request with {@link RequestOptions#addHeader}
     * <p><strong>Request Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     userActionValue: String (Required)
     * }
     * }
     * </pre>
     * 
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     userActionResult: String (Required)
     * }
     * }
     * </pre>
     * 
     * @param id The user's id.
     * @param body The body parameter.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return user action response along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<BinaryData>> repeatableActionWithResponseAsync(int id, BinaryData body,
        RequestOptions requestOptions) {
        final String contentType = "application/json";
        final String accept = "application/json";
        RequestOptions requestOptionsLocal = requestOptions == null ? new RequestOptions() : requestOptions;
        requestOptionsLocal.addRequestCallback(requestLocal -> {
            if (requestLocal.getHeaders().get(HttpHeaderName.fromString("repeatability-request-id")) == null) {
                requestLocal.getHeaders()
                    .set(HttpHeaderName.fromString("repeatability-request-id"), CoreUtils.randomUuid().toString());
            }
        });
        requestOptionsLocal.addRequestCallback(requestLocal -> {
            if (requestLocal.getHeaders().get(HttpHeaderName.fromString("repeatability-first-sent")) == null) {
                requestLocal.getHeaders()
                    .set(HttpHeaderName.fromString("repeatability-first-sent"),
                        DateTimeRfc1123.toRfc1123String(OffsetDateTime.now()));
            }
        });
        return FluxUtil.withContext(context -> service.repeatableAction(this.getEndpoint(),
            this.getServiceVersion().getVersion(), id, contentType, accept, body, requestOptionsLocal, context));
    }

    /**
     * Test for repeatable requests.
     * <p><strong>Header Parameters</strong></p>
     * <table border="1">
     * <caption>Header Parameters</caption>
     * <tr><th>Name</th><th>Type</th><th>Required</th><th>Description</th></tr>
     * <tr><td>repeatability-request-id</td><td>String</td><td>No</td><td>Repeatability request ID header</td></tr>
     * <tr><td>repeatability-first-sent</td><td>String</td><td>No</td><td>Repeatability first sent header as
     * HTTP-date</td></tr>
     * </table>
     * You can add these to a request with {@link RequestOptions#addHeader}
     * <p><strong>Request Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     userActionValue: String (Required)
     * }
     * }
     * </pre>
     * 
     * <p><strong>Response Body Schema</strong></p>
     * 
     * <pre>
     * {@code
     * {
     *     userActionResult: String (Required)
     * }
     * }
     * </pre>
     * 
     * @param id The user's id.
     * @param body The body parameter.
     * @param requestOptions The options to configure the HTTP request before HTTP client sends it.
     * @throws HttpResponseException thrown if the request is rejected by server.
     * @throws ClientAuthenticationException thrown if the request is rejected by server on status code 401.
     * @throws ResourceNotFoundException thrown if the request is rejected by server on status code 404.
     * @throws ResourceModifiedException thrown if the request is rejected by server on status code 409.
     * @return user action response along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<BinaryData> repeatableActionWithResponse(int id, BinaryData body, RequestOptions requestOptions) {
        final String contentType = "application/json";
        final String accept = "application/json";
        RequestOptions requestOptionsLocal = requestOptions == null ? new RequestOptions() : requestOptions;
        requestOptionsLocal.addRequestCallback(requestLocal -> {
            if (requestLocal.getHeaders().get(HttpHeaderName.fromString("repeatability-request-id")) == null) {
                requestLocal.getHeaders()
                    .set(HttpHeaderName.fromString("repeatability-request-id"), CoreUtils.randomUuid().toString());
            }
        });
        requestOptionsLocal.addRequestCallback(requestLocal -> {
            if (requestLocal.getHeaders().get(HttpHeaderName.fromString("repeatability-first-sent")) == null) {
                requestLocal.getHeaders()
                    .set(HttpHeaderName.fromString("repeatability-first-sent"),
                        DateTimeRfc1123.toRfc1123String(OffsetDateTime.now()));
            }
        });
        return service.repeatableActionSync(this.getEndpoint(), this.getServiceVersion().getVersion(), id, contentType,
            accept, body, requestOptionsLocal, Context.NONE);
    }
}