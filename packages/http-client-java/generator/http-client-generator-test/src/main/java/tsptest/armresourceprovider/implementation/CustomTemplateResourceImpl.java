// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.armresourceprovider.implementation;

import com.azure.core.management.Region;
import com.azure.core.management.SystemData;
import com.azure.core.util.Context;
import java.util.Collections;
import java.util.Map;
import tsptest.armresourceprovider.fluent.models.CustomTemplateResourceInner;
import tsptest.armresourceprovider.models.AnonymousEmptyModel;
import tsptest.armresourceprovider.models.CustomTemplateResource;
import tsptest.armresourceprovider.models.CustomTemplateResourcePatch;
import tsptest.armresourceprovider.models.Dog;
import tsptest.armresourceprovider.models.EmptyModel;
import tsptest.armresourceprovider.models.ManagedServiceIdentity;
import tsptest.armresourceprovider.models.PriorityModel;
import tsptest.armresourceprovider.models.ProvisioningState;

public final class CustomTemplateResourceImpl
    implements CustomTemplateResource, CustomTemplateResource.Definition, CustomTemplateResource.Update {
    private CustomTemplateResourceInner innerObject;

    private final tsptest.armresourceprovider.ArmResourceProviderManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public String location() {
        return this.innerModel().location();
    }

    public Map<String, String> tags() {
        Map<String, String> inner = this.innerModel().tags();
        if (inner != null) {
            return Collections.unmodifiableMap(inner);
        } else {
            return Collections.emptyMap();
        }
    }

    public ManagedServiceIdentity identity() {
        return this.innerModel().identity();
    }

    public SystemData systemData() {
        return this.innerModel().systemData();
    }

    public ProvisioningState provisioningState() {
        return this.innerModel().provisioningState();
    }

    public Dog dog() {
        return this.innerModel().dog();
    }

    public EmptyModel namedEmptyModel() {
        return this.innerModel().namedEmptyModel();
    }

    public AnonymousEmptyModel anonymousEmptyModel() {
        return this.innerModel().anonymousEmptyModel();
    }

    public PriorityModel priority() {
        return this.innerModel().priority();
    }

    public Region region() {
        return Region.fromName(this.regionName());
    }

    public String regionName() {
        return this.location();
    }

    public String resourceGroupName() {
        return resourceGroupName;
    }

    public CustomTemplateResourceInner innerModel() {
        return this.innerObject;
    }

    private tsptest.armresourceprovider.ArmResourceProviderManager manager() {
        return this.serviceManager;
    }

    private String resourceGroupName;

    private String customTemplateResourceName;

    private String createIfMatch;

    private String createIfNoneMatch;

    private CustomTemplateResourcePatch updateProperties;

    public CustomTemplateResourceImpl withExistingResourceGroup(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    public CustomTemplateResource create() {
        this.innerObject = serviceManager.serviceClient()
            .getCustomTemplateResourceInterfaces()
            .createOrUpdate(resourceGroupName, customTemplateResourceName, this.innerModel(), createIfMatch,
                createIfNoneMatch, Context.NONE);
        return this;
    }

    public CustomTemplateResource create(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getCustomTemplateResourceInterfaces()
            .createOrUpdate(resourceGroupName, customTemplateResourceName, this.innerModel(), createIfMatch,
                createIfNoneMatch, context);
        return this;
    }

    CustomTemplateResourceImpl(String name, tsptest.armresourceprovider.ArmResourceProviderManager serviceManager) {
        this.innerObject = new CustomTemplateResourceInner();
        this.serviceManager = serviceManager;
        this.customTemplateResourceName = name;
        this.createIfMatch = null;
        this.createIfNoneMatch = null;
    }

    public CustomTemplateResourceImpl update() {
        this.updateProperties = new CustomTemplateResourcePatch();
        return this;
    }

    public CustomTemplateResource apply() {
        this.innerObject = serviceManager.serviceClient()
            .getCustomTemplateResourceInterfaces()
            .updateLongRunning(resourceGroupName, customTemplateResourceName, updateProperties, Context.NONE);
        return this;
    }

    public CustomTemplateResource apply(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getCustomTemplateResourceInterfaces()
            .updateLongRunning(resourceGroupName, customTemplateResourceName, updateProperties, context);
        return this;
    }

    CustomTemplateResourceImpl(CustomTemplateResourceInner innerObject,
        tsptest.armresourceprovider.ArmResourceProviderManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.resourceGroupName = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "resourceGroups");
        this.customTemplateResourceName
            = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "customTemplateResources");
    }

    public CustomTemplateResourceImpl withRegion(Region location) {
        this.innerModel().withLocation(location.toString());
        return this;
    }

    public CustomTemplateResourceImpl withRegion(String location) {
        this.innerModel().withLocation(location);
        return this;
    }

    public CustomTemplateResourceImpl withTags(Map<String, String> tags) {
        this.innerModel().withTags(tags);
        return this;
    }

    public CustomTemplateResourceImpl withIdentity(ManagedServiceIdentity identity) {
        if (isInCreateMode()) {
            this.innerModel().withIdentity(identity);
            return this;
        } else {
            this.updateProperties.withIdentity(identity);
            return this;
        }
    }

    public CustomTemplateResourceImpl withDog(Dog dog) {
        this.innerModel().withDog(dog);
        return this;
    }

    public CustomTemplateResourceImpl withNamedEmptyModel(EmptyModel namedEmptyModel) {
        this.innerModel().withNamedEmptyModel(namedEmptyModel);
        return this;
    }

    public CustomTemplateResourceImpl withAnonymousEmptyModel(AnonymousEmptyModel anonymousEmptyModel) {
        this.innerModel().withAnonymousEmptyModel(anonymousEmptyModel);
        return this;
    }

    public CustomTemplateResourceImpl withPriority(PriorityModel priority) {
        this.innerModel().withPriority(priority);
        return this;
    }

    public CustomTemplateResourceImpl withIfMatch(String ifMatch) {
        this.createIfMatch = ifMatch;
        return this;
    }

    public CustomTemplateResourceImpl withIfNoneMatch(String ifNoneMatch) {
        this.createIfNoneMatch = ifNoneMatch;
        return this;
    }

    private boolean isInCreateMode() {
        return this.innerModel() == null || this.innerModel().id() == null;
    }
}
