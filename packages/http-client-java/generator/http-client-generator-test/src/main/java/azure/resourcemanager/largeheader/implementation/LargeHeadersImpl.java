// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package azure.resourcemanager.largeheader.implementation;

import azure.resourcemanager.largeheader.fluent.LargeHeadersClient;
import azure.resourcemanager.largeheader.fluent.models.CancelResultInner;
import azure.resourcemanager.largeheader.models.CancelResult;
import azure.resourcemanager.largeheader.models.LargeHeaders;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;

public final class LargeHeadersImpl implements LargeHeaders {
    private static final ClientLogger LOGGER = new ClientLogger(LargeHeadersImpl.class);

    private final LargeHeadersClient innerClient;

    private final azure.resourcemanager.largeheader.LargeHeaderManager serviceManager;

    public LargeHeadersImpl(LargeHeadersClient innerClient,
        azure.resourcemanager.largeheader.LargeHeaderManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public CancelResult two6k(String resourceGroupName, String largeHeaderName) {
        CancelResultInner inner = this.serviceClient().two6k(resourceGroupName, largeHeaderName);
        if (inner != null) {
            return new CancelResultImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public CancelResult two6k(String resourceGroupName, String largeHeaderName, Context context) {
        CancelResultInner inner = this.serviceClient().two6k(resourceGroupName, largeHeaderName, context);
        if (inner != null) {
            return new CancelResultImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    private LargeHeadersClient serviceClient() {
        return this.innerClient;
    }

    private azure.resourcemanager.largeheader.LargeHeaderManager manager() {
        return this.serviceManager;
    }
}
