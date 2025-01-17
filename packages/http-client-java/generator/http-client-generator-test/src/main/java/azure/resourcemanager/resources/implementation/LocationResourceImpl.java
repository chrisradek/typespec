// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package azure.resourcemanager.resources.implementation;

import azure.resourcemanager.resources.fluent.models.LocationResourceInner;
import azure.resourcemanager.resources.models.LocationResource;
import azure.resourcemanager.resources.models.LocationResourceProperties;
import com.azure.core.management.SystemData;
import com.azure.core.util.Context;

public final class LocationResourceImpl
    implements LocationResource, LocationResource.Definition, LocationResource.Update {
    private LocationResourceInner innerObject;

    private final azure.resourcemanager.resources.ResourcesManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public LocationResourceProperties properties() {
        return this.innerModel().properties();
    }

    public SystemData systemData() {
        return this.innerModel().systemData();
    }

    public LocationResourceInner innerModel() {
        return this.innerObject;
    }

    private azure.resourcemanager.resources.ResourcesManager manager() {
        return this.serviceManager;
    }

    private String location;

    private String locationResourceName;

    public LocationResourceImpl withExistingLocation(String location) {
        this.location = location;
        return this;
    }

    public LocationResource create() {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .createOrUpdateWithResponse(location, locationResourceName, this.innerModel(), Context.NONE)
            .getValue();
        return this;
    }

    public LocationResource create(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .createOrUpdateWithResponse(location, locationResourceName, this.innerModel(), context)
            .getValue();
        return this;
    }

    LocationResourceImpl(String name, azure.resourcemanager.resources.ResourcesManager serviceManager) {
        this.innerObject = new LocationResourceInner();
        this.serviceManager = serviceManager;
        this.locationResourceName = name;
    }

    public LocationResourceImpl update() {
        return this;
    }

    public LocationResource apply() {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .updateWithResponse(location, locationResourceName, this.innerModel(), Context.NONE)
            .getValue();
        return this;
    }

    public LocationResource apply(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .updateWithResponse(location, locationResourceName, this.innerModel(), context)
            .getValue();
        return this;
    }

    LocationResourceImpl(LocationResourceInner innerObject,
        azure.resourcemanager.resources.ResourcesManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.location = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "locations");
        this.locationResourceName = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "locationResources");
    }

    public LocationResource refresh() {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .getWithResponse(location, locationResourceName, Context.NONE)
            .getValue();
        return this;
    }

    public LocationResource refresh(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getLocationResources()
            .getWithResponse(location, locationResourceName, context)
            .getValue();
        return this;
    }

    public LocationResourceImpl withProperties(LocationResourceProperties properties) {
        this.innerModel().withProperties(properties);
        return this;
    }
}