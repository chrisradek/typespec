package routes.implementation;

import io.clientcore.core.annotations.ReturnType;
import io.clientcore.core.annotations.ServiceInterface;
import io.clientcore.core.annotations.ServiceMethod;
import io.clientcore.core.http.annotations.HostParam;
import io.clientcore.core.http.annotations.HttpRequestInformation;
import io.clientcore.core.http.annotations.UnexpectedResponseExceptionDetail;
import io.clientcore.core.http.models.HttpMethod;
import io.clientcore.core.http.models.HttpResponseException;
import io.clientcore.core.http.models.RequestContext;
import io.clientcore.core.http.models.Response;
import io.clientcore.core.http.pipeline.HttpPipeline;
import io.clientcore.core.instrumentation.Instrumentation;
import java.lang.reflect.InvocationTargetException;

/**
 * Initializes a new instance of the RoutesClient type.
 */
public final class RoutesClientImpl {
    /**
     * The proxy service used to perform REST calls.
     */
    private final RoutesClientService service;

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
     * The instance of instrumentation to report telemetry.
     */
    private final Instrumentation instrumentation;

    /**
     * Gets The instance of instrumentation to report telemetry.
     * 
     * @return the instrumentation value.
     */
    public Instrumentation getInstrumentation() {
        return this.instrumentation;
    }

    /**
     * The PathParametersImpl object to access its operations.
     */
    private final PathParametersImpl pathParameters;

    /**
     * Gets the PathParametersImpl object to access its operations.
     * 
     * @return the PathParametersImpl object.
     */
    public PathParametersImpl getPathParameters() {
        return this.pathParameters;
    }

    /**
     * The PathParametersReservedExpansionsImpl object to access its operations.
     */
    private final PathParametersReservedExpansionsImpl pathParametersReservedExpansions;

    /**
     * Gets the PathParametersReservedExpansionsImpl object to access its operations.
     * 
     * @return the PathParametersReservedExpansionsImpl object.
     */
    public PathParametersReservedExpansionsImpl getPathParametersReservedExpansions() {
        return this.pathParametersReservedExpansions;
    }

    /**
     * The PathParametersSimpleExpansionStandardsImpl object to access its operations.
     */
    private final PathParametersSimpleExpansionStandardsImpl pathParametersSimpleExpansionStandards;

    /**
     * Gets the PathParametersSimpleExpansionStandardsImpl object to access its operations.
     * 
     * @return the PathParametersSimpleExpansionStandardsImpl object.
     */
    public PathParametersSimpleExpansionStandardsImpl getPathParametersSimpleExpansionStandards() {
        return this.pathParametersSimpleExpansionStandards;
    }

    /**
     * The PathParametersSimpleExpansionExplodesImpl object to access its operations.
     */
    private final PathParametersSimpleExpansionExplodesImpl pathParametersSimpleExpansionExplodes;

    /**
     * Gets the PathParametersSimpleExpansionExplodesImpl object to access its operations.
     * 
     * @return the PathParametersSimpleExpansionExplodesImpl object.
     */
    public PathParametersSimpleExpansionExplodesImpl getPathParametersSimpleExpansionExplodes() {
        return this.pathParametersSimpleExpansionExplodes;
    }

    /**
     * The PathParametersPathExpansionStandardsImpl object to access its operations.
     */
    private final PathParametersPathExpansionStandardsImpl pathParametersPathExpansionStandards;

    /**
     * Gets the PathParametersPathExpansionStandardsImpl object to access its operations.
     * 
     * @return the PathParametersPathExpansionStandardsImpl object.
     */
    public PathParametersPathExpansionStandardsImpl getPathParametersPathExpansionStandards() {
        return this.pathParametersPathExpansionStandards;
    }

    /**
     * The PathParametersPathExpansionExplodesImpl object to access its operations.
     */
    private final PathParametersPathExpansionExplodesImpl pathParametersPathExpansionExplodes;

    /**
     * Gets the PathParametersPathExpansionExplodesImpl object to access its operations.
     * 
     * @return the PathParametersPathExpansionExplodesImpl object.
     */
    public PathParametersPathExpansionExplodesImpl getPathParametersPathExpansionExplodes() {
        return this.pathParametersPathExpansionExplodes;
    }

    /**
     * The PathParametersLabelExpansionStandardsImpl object to access its operations.
     */
    private final PathParametersLabelExpansionStandardsImpl pathParametersLabelExpansionStandards;

    /**
     * Gets the PathParametersLabelExpansionStandardsImpl object to access its operations.
     * 
     * @return the PathParametersLabelExpansionStandardsImpl object.
     */
    public PathParametersLabelExpansionStandardsImpl getPathParametersLabelExpansionStandards() {
        return this.pathParametersLabelExpansionStandards;
    }

    /**
     * The PathParametersLabelExpansionExplodesImpl object to access its operations.
     */
    private final PathParametersLabelExpansionExplodesImpl pathParametersLabelExpansionExplodes;

    /**
     * Gets the PathParametersLabelExpansionExplodesImpl object to access its operations.
     * 
     * @return the PathParametersLabelExpansionExplodesImpl object.
     */
    public PathParametersLabelExpansionExplodesImpl getPathParametersLabelExpansionExplodes() {
        return this.pathParametersLabelExpansionExplodes;
    }

    /**
     * The PathParametersMatrixExpansionStandardsImpl object to access its operations.
     */
    private final PathParametersMatrixExpansionStandardsImpl pathParametersMatrixExpansionStandards;

    /**
     * Gets the PathParametersMatrixExpansionStandardsImpl object to access its operations.
     * 
     * @return the PathParametersMatrixExpansionStandardsImpl object.
     */
    public PathParametersMatrixExpansionStandardsImpl getPathParametersMatrixExpansionStandards() {
        return this.pathParametersMatrixExpansionStandards;
    }

    /**
     * The PathParametersMatrixExpansionExplodesImpl object to access its operations.
     */
    private final PathParametersMatrixExpansionExplodesImpl pathParametersMatrixExpansionExplodes;

    /**
     * Gets the PathParametersMatrixExpansionExplodesImpl object to access its operations.
     * 
     * @return the PathParametersMatrixExpansionExplodesImpl object.
     */
    public PathParametersMatrixExpansionExplodesImpl getPathParametersMatrixExpansionExplodes() {
        return this.pathParametersMatrixExpansionExplodes;
    }

    /**
     * The QueryParametersImpl object to access its operations.
     */
    private final QueryParametersImpl queryParameters;

    /**
     * Gets the QueryParametersImpl object to access its operations.
     * 
     * @return the QueryParametersImpl object.
     */
    public QueryParametersImpl getQueryParameters() {
        return this.queryParameters;
    }

    /**
     * The QueryParametersQueryExpansionStandardsImpl object to access its operations.
     */
    private final QueryParametersQueryExpansionStandardsImpl queryParametersQueryExpansionStandards;

    /**
     * Gets the QueryParametersQueryExpansionStandardsImpl object to access its operations.
     * 
     * @return the QueryParametersQueryExpansionStandardsImpl object.
     */
    public QueryParametersQueryExpansionStandardsImpl getQueryParametersQueryExpansionStandards() {
        return this.queryParametersQueryExpansionStandards;
    }

    /**
     * The QueryParametersQueryExpansionExplodesImpl object to access its operations.
     */
    private final QueryParametersQueryExpansionExplodesImpl queryParametersQueryExpansionExplodes;

    /**
     * Gets the QueryParametersQueryExpansionExplodesImpl object to access its operations.
     * 
     * @return the QueryParametersQueryExpansionExplodesImpl object.
     */
    public QueryParametersQueryExpansionExplodesImpl getQueryParametersQueryExpansionExplodes() {
        return this.queryParametersQueryExpansionExplodes;
    }

    /**
     * The QueryParametersQueryContinuationStandardsImpl object to access its operations.
     */
    private final QueryParametersQueryContinuationStandardsImpl queryParametersQueryContinuationStandards;

    /**
     * Gets the QueryParametersQueryContinuationStandardsImpl object to access its operations.
     * 
     * @return the QueryParametersQueryContinuationStandardsImpl object.
     */
    public QueryParametersQueryContinuationStandardsImpl getQueryParametersQueryContinuationStandards() {
        return this.queryParametersQueryContinuationStandards;
    }

    /**
     * The QueryParametersQueryContinuationExplodesImpl object to access its operations.
     */
    private final QueryParametersQueryContinuationExplodesImpl queryParametersQueryContinuationExplodes;

    /**
     * Gets the QueryParametersQueryContinuationExplodesImpl object to access its operations.
     * 
     * @return the QueryParametersQueryContinuationExplodesImpl object.
     */
    public QueryParametersQueryContinuationExplodesImpl getQueryParametersQueryContinuationExplodes() {
        return this.queryParametersQueryContinuationExplodes;
    }

    /**
     * The InInterfacesImpl object to access its operations.
     */
    private final InInterfacesImpl inInterfaces;

    /**
     * Gets the InInterfacesImpl object to access its operations.
     * 
     * @return the InInterfacesImpl object.
     */
    public InInterfacesImpl getInInterfaces() {
        return this.inInterfaces;
    }

    /**
     * Initializes an instance of RoutesClient client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param instrumentation The instance of instrumentation to report telemetry.
     * @param endpoint Service host.
     */
    public RoutesClientImpl(HttpPipeline httpPipeline, Instrumentation instrumentation, String endpoint) {
        this.httpPipeline = httpPipeline;
        this.instrumentation = instrumentation;
        this.endpoint = endpoint;
        this.pathParameters = new PathParametersImpl(this);
        this.pathParametersReservedExpansions = new PathParametersReservedExpansionsImpl(this);
        this.pathParametersSimpleExpansionStandards = new PathParametersSimpleExpansionStandardsImpl(this);
        this.pathParametersSimpleExpansionExplodes = new PathParametersSimpleExpansionExplodesImpl(this);
        this.pathParametersPathExpansionStandards = new PathParametersPathExpansionStandardsImpl(this);
        this.pathParametersPathExpansionExplodes = new PathParametersPathExpansionExplodesImpl(this);
        this.pathParametersLabelExpansionStandards = new PathParametersLabelExpansionStandardsImpl(this);
        this.pathParametersLabelExpansionExplodes = new PathParametersLabelExpansionExplodesImpl(this);
        this.pathParametersMatrixExpansionStandards = new PathParametersMatrixExpansionStandardsImpl(this);
        this.pathParametersMatrixExpansionExplodes = new PathParametersMatrixExpansionExplodesImpl(this);
        this.queryParameters = new QueryParametersImpl(this);
        this.queryParametersQueryExpansionStandards = new QueryParametersQueryExpansionStandardsImpl(this);
        this.queryParametersQueryExpansionExplodes = new QueryParametersQueryExpansionExplodesImpl(this);
        this.queryParametersQueryContinuationStandards = new QueryParametersQueryContinuationStandardsImpl(this);
        this.queryParametersQueryContinuationExplodes = new QueryParametersQueryContinuationExplodesImpl(this);
        this.inInterfaces = new InInterfacesImpl(this);
        this.service = RoutesClientService.getNewInstance(this.httpPipeline);
    }

    /**
     * The interface defining all the services for RoutesClient to be used by the proxy service to perform REST calls.
     */
    @ServiceInterface(name = "RoutesClient", host = "{endpoint}")
    public interface RoutesClientService {
        static RoutesClientService getNewInstance(HttpPipeline pipeline) {
            try {
                Class<?> clazz = Class.forName("routes.implementation.RoutesClientServiceImpl");
                return (RoutesClientService) clazz.getMethod("getNewInstance", HttpPipeline.class)
                    .invoke(null, pipeline);
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

        }

        @HttpRequestInformation(method = HttpMethod.GET, path = "/routes/fixed", expectedStatusCodes = { 204 })
        @UnexpectedResponseExceptionDetail
        Response<Void> fixed(@HostParam("endpoint") String endpoint, RequestContext requestContext);
    }

    /**
     * The fixed operation.
     * 
     * @param requestContext The context to configure the HTTP request before HTTP client sends it.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws HttpResponseException thrown if the service returns an error.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> fixedWithResponse(RequestContext requestContext) {
        return this.instrumentation.instrumentWithResponse("Routes.fixed", requestContext, updatedContext -> {
            return service.fixed(this.getEndpoint(), updatedContext);
        });
    }
}
