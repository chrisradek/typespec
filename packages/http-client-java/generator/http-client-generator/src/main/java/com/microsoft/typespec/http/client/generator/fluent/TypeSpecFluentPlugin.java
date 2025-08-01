// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.typespec.http.client.generator.fluent;

import com.azure.core.util.CoreUtils;
import com.azure.json.JsonReader;
import com.azure.json.ReadValueCallback;
import com.microsoft.typespec.http.client.generator.JavaSettingsAccessor;
import com.microsoft.typespec.http.client.generator.TypeSpecPlugin;
import com.microsoft.typespec.http.client.generator.core.extension.model.Message;
import com.microsoft.typespec.http.client.generator.core.extension.model.codemodel.CodeModel;
import com.microsoft.typespec.http.client.generator.core.extension.plugin.JavaSettings;
import com.microsoft.typespec.http.client.generator.core.mapper.Mappers;
import com.microsoft.typespec.http.client.generator.core.model.clientmodel.Client;
import com.microsoft.typespec.http.client.generator.core.model.clientmodel.TypeSpecMetadata;
import com.microsoft.typespec.http.client.generator.core.model.javamodel.JavaVisibility;
import com.microsoft.typespec.http.client.generator.core.util.ClientModelUtil;
import com.microsoft.typespec.http.client.generator.mgmt.FluentGen;
import com.microsoft.typespec.http.client.generator.mgmt.FluentNamer;
import com.microsoft.typespec.http.client.generator.mgmt.mapper.FluentMapper;
import com.microsoft.typespec.http.client.generator.mgmt.model.javamodel.FluentJavaPackage;
import com.microsoft.typespec.http.client.generator.mgmt.util.FluentUtils;
import com.microsoft.typespec.http.client.generator.model.EmitterOptions;
import com.microsoft.typespec.http.client.generator.util.FileUtil;
import com.microsoft.typespec.http.client.generator.util.MetadataUtil;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeSpecFluentPlugin extends FluentGen {
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeSpecFluentPlugin.class);
    private final EmitterOptions emitterOptions;

    public TypeSpecFluentPlugin(EmitterOptions emitterOptions, boolean sdkIntegration) {
        super(new TypeSpecPlugin.MockConnection(), "dummy", "dummy");
        this.emitterOptions = emitterOptions;
        SETTINGS_MAP.put("namespace", emitterOptions.getNamespace());
        if (!CoreUtils.isNullOrEmpty(emitterOptions.getOutputDir())) {
            SETTINGS_MAP.put("output-folder", emitterOptions.getOutputDir());
        }
        if (!CoreUtils.isNullOrEmpty(emitterOptions.getServiceName())) {
            SETTINGS_MAP.put("service-name", emitterOptions.getServiceName());
        }
        if (emitterOptions.getGenerateSamples() != null) {
            SETTINGS_MAP.put("generate-samples", emitterOptions.getGenerateSamples());
        }
        if (emitterOptions.getGenerateTests() != null) {
            SETTINGS_MAP.put("generate-tests", emitterOptions.getGenerateTests());
        }
        if (emitterOptions.getArm()) {
            SETTINGS_MAP.put("fluent", "lite");
        }
        if (emitterOptions.getPackageVersion() != null) {
            SETTINGS_MAP.put("package-version", emitterOptions.getPackageVersion());
        }
        if (emitterOptions.getEnableSyncStack() != null) {
            SETTINGS_MAP.put("enable-sync-stack", emitterOptions.getEnableSyncStack());
        }
        SETTINGS_MAP.put("sdk-integration", sdkIntegration);
        SETTINGS_MAP.put("output-model-immutable", true);
        SETTINGS_MAP.put("uuid-as-string", true);
        SETTINGS_MAP.put("stream-style-serialization", emitterOptions.getStreamStyleSerialization());
        SETTINGS_MAP.put("use-object-for-unknown", emitterOptions.getUseObjectForUnknown());

        // mgmt
        if (emitterOptions.getRenameModel() != null) {
            SETTINGS_MAP.put("rename-model", emitterOptions.getRenameModel());
        }
        if (emitterOptions.getAddInner() != null) {
            SETTINGS_MAP.put("add-inner", emitterOptions.getAddInner());
        }
        if (emitterOptions.getRemoveInner() != null) {
            SETTINGS_MAP.put("remove-inner", emitterOptions.getRemoveInner());
        }
        if (emitterOptions.getPreserveModel() != null) {
            SETTINGS_MAP.put("preserve-model", emitterOptions.getPreserveModel());
        }
        if (emitterOptions.getGenerateAsyncMethods() != null) {
            SETTINGS_MAP.put("generate-async-methods", emitterOptions.getGenerateAsyncMethods());
        }
        if (emitterOptions.getResourceCollectionAssociations() != null) {
            SETTINGS_MAP.put("resource-collection-associations", emitterOptions.getResourceCollectionAssociations());
        }

        JavaSettingsAccessor.setHost(this);
        LOGGER.info("Output folder: {}", emitterOptions.getOutputDir());
        LOGGER.info("Namespace: {}", JavaSettings.getInstance().getPackage());
    }

    public CodeModel preProcess(CodeModel codeModel) {
        // transform code model
        FluentNamer fluentNamer = new TypeSpecFluentNamer(this, pluginName, sessionId, SETTINGS_MAP, codeModel);
        return fluentNamer.processCodeModel();
    }

    public Client processClient(CodeModel codeModel) {
        // call FluentGen.handleMap
        return handleMap(codeModel);
    }

    public FluentJavaPackage processTemplates(CodeModel codeModel, Client client) {
        final String apiVersion = emitterOptions.getApiVersion() == null
            ? MetadataUtil.getLatestApiVersionFromClient(codeModel)
            : emitterOptions.getApiVersion();

        FluentJavaPackage javaPackage = handleTemplate(client);
        handleFluentLite(codeModel, client, javaPackage, apiVersion);

        if (emitterOptions.getIncludeApiViewProperties() == Boolean.TRUE) {
            TypeSpecMetadata metadata = new TypeSpecMetadata(FluentUtils.getArtifactId(), emitterOptions.getFlavor(),
                apiVersion, collectCrossLanguageDefinitions(client));
            javaPackage.addTypeSpecMetadata(metadata);
        }

        return javaPackage;
    }

    @Override
    public void writeFile(String fileName, String content, List<Object> sourceMap) {
        File outputFile = FileUtil.writeToFile(emitterOptions.getOutputDir(), fileName, content);
        LOGGER.info("Write file: {}", outputFile.getAbsolutePath());
    }

    @Override
    protected FluentMapper getFluentMapper() {
        FluentMapper fluentMapper = super.getFluentMapper();
        Mappers.setFactory(new TypeSpecFluentMapperFactory());
        return fluentMapper;
    }

    private static final Map<String, Object> SETTINGS_MAP = new HashMap<>();

    // from fluentnamer/readme.md
    static {
        SETTINGS_MAP.put("data-plane", false);

        SETTINGS_MAP.put("sdk-integration", true);
        SETTINGS_MAP.put("regenerate-pom", true);

        SETTINGS_MAP.put("license-header", "MICROSOFT_MIT_SMALL_TYPESPEC");

        SETTINGS_MAP.put("generic-response-type", false);
        SETTINGS_MAP.put("generate-client-interfaces", true);
        SETTINGS_MAP.put("client-logger", true);

        SETTINGS_MAP.put("required-parameter-client-methods", true);
        SETTINGS_MAP.put("client-flattened-annotation-target", "none");
        SETTINGS_MAP.put("null-byte-array-maps-to-empty-array", true);
        SETTINGS_MAP.put("graal-vm-config", true);
        SETTINGS_MAP.put("sync-methods", "all");
        SETTINGS_MAP.put("client-side-validations", true);
        SETTINGS_MAP.put("stream-style-serialization", false);
//        SETTINGS_MAP.put("pipeline.fluentgen.naming.override", getNamingOverrides());
    }

    private static Map<String, String> getNamingOverrides() {
        Map<String, String> namingOverrides = new HashMap<>();
        namingOverrides.put("eTag", "etag");
        namingOverrides.put("userName", "username");
        namingOverrides.put("metaData", "metadata");
        namingOverrides.put("timeStamp", "timestamp");
        namingOverrides.put("hostName", "hostname");
        namingOverrides.put("webHook", "webhook");
        namingOverrides.put("coolDown", "cooldown");
        namingOverrides.put("resourceregion", "resourceRegion");
        namingOverrides.put("sTag", "stag");
        namingOverrides.put("tagname", "tagName");
        namingOverrides.put("tagvalue", "tagValue");

        return namingOverrides;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String key, ReadValueCallback<String, T> converter) {
        return (T) SETTINGS_MAP.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValueWithJsonReader(String key, ReadValueCallback<JsonReader, T> converter) {
        return (T) SETTINGS_MAP.get(key);
    }

    @Override
    public void message(Message message) {
        String log = message.getText();
        switch (message.getChannel()) {
            case INFORMATION:
                LOGGER.info(log);
                break;

            case WARNING:
                LOGGER.warn(log);
                break;

            case ERROR:
            case FATAL:
                LOGGER.error(log);
                break;

            case DEBUG:
                LOGGER.debug(log);
                break;

            default:
                LOGGER.info(log);
                break;
        }
    }

    private Map<String, String> collectCrossLanguageDefinitions(Client client) {
        if (!JavaSettings.getInstance().isFluentLite()) {
            return null;
        }

        final Map<String, String> crossLanguageDefinitionsMap = new TreeMap<>();

        String interfacePackage = ClientModelUtil.getServiceClientInterfacePackageName();

        // Client interface
        crossLanguageDefinitionsMap.put(interfacePackage + "." + client.getServiceClient().getInterfaceName(),
            client.getServiceClient().getCrossLanguageDefinitionId());

        client.getServiceClient()
            .getMethodGroupClients()
            .forEach(methodGroupClient -> crossLanguageDefinitionsMap.put(
                interfacePackage + "." + methodGroupClient.getInterfaceName(),
                methodGroupClient.getCrossLanguageDefinitionId()));

        client.getClientBuilders()
            .forEach(clientBuilder -> crossLanguageDefinitionsMap.put(
                clientBuilder.getPackageName() + "." + clientBuilder.getClassName(),
                clientBuilder.getCrossLanguageDefinitionId()));

        // Methods
        client.getServiceClient()
            .getMethodGroupClients()
            .forEach(methodGroupClient -> methodGroupClient.getClientMethods().forEach(method -> {
                if (method.getMethodVisibility() == JavaVisibility.Public) {
                    crossLanguageDefinitionsMap.put(
                        interfacePackage + "." + methodGroupClient.getInterfaceName() + "." + method.getName(),
                        method.getCrossLanguageDefinitionId());
                }
            }));

        // Client model
        client.getModels().forEach(model -> {
            crossLanguageDefinitionsMap.put(model.getPackage() + "." + model.getName(),
                model.getCrossLanguageDefinitionId());
        });

        // Enum
        client.getEnums().forEach(model -> {
            crossLanguageDefinitionsMap.put(model.getPackage() + "." + model.getName(),
                model.getCrossLanguageDefinitionId());
        });

        return crossLanguageDefinitionsMap;
    }
}
