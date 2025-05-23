package type.property.nullable;

import io.clientcore.core.annotations.Metadata;
import io.clientcore.core.annotations.MetadataProperties;
import io.clientcore.core.serialization.json.JsonReader;
import io.clientcore.core.serialization.json.JsonSerializable;
import io.clientcore.core.serialization.json.JsonToken;
import io.clientcore.core.serialization.json.JsonWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import type.property.nullable.implementation.JsonMergePatchHelper;

/**
 * Inner model used in collections model property.
 */
@Metadata(properties = { MetadataProperties.FLUENT })
public final class InnerModel implements JsonSerializable<InnerModel> {
    /*
     * Inner model property
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    private String property;

    /**
     * Stores updated model property, the value is property name, not serialized name.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    private final Set<String> updatedProperties = new HashSet<>();

    @Metadata(properties = { MetadataProperties.GENERATED })
    private boolean jsonMergePatch;

    @Metadata(properties = { MetadataProperties.GENERATED })
    private void serializeAsJsonMergePatch(boolean jsonMergePatch) {
        this.jsonMergePatch = jsonMergePatch;
    }

    static {
        JsonMergePatchHelper.setInnerModelAccessor(new JsonMergePatchHelper.InnerModelAccessor() {
            @Override
            public InnerModel prepareModelForJsonMergePatch(InnerModel model, boolean jsonMergePatchEnabled) {
                model.serializeAsJsonMergePatch(jsonMergePatchEnabled);
                return model;
            }

            @Override
            public boolean isJsonMergePatch(InnerModel model) {
                return model.jsonMergePatch;
            }
        });
    }

    /**
     * Creates an instance of InnerModel class.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public InnerModel() {
    }

    /**
     * Get the property property: Inner model property.
     * 
     * @return the property value.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public String getProperty() {
        return this.property;
    }

    /**
     * Set the property property: Inner model property.
     * <p>Required when create the resource.</p>
     * 
     * @param property the property value to set.
     * @return the InnerModel object itself.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public InnerModel setProperty(String property) {
        this.property = property;
        this.updatedProperties.add("property");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        if (jsonMergePatch) {
            return toJsonMergePatch(jsonWriter);
        } else {
            jsonWriter.writeStartObject();
            jsonWriter.writeStringField("property", this.property);
            return jsonWriter.writeEndObject();
        }
    }

    @Metadata(properties = { MetadataProperties.GENERATED })
    private JsonWriter toJsonMergePatch(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        if (updatedProperties.contains("property")) {
            if (this.property == null) {
                jsonWriter.writeNullField("property");
            } else {
                jsonWriter.writeStringField("property", this.property);
            }
        }
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of InnerModel from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of InnerModel if the JsonReader was pointing to an instance of it, or null if it was pointing
     * to JSON null.
     * @throws IOException If an error occurs while reading the InnerModel.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public static InnerModel fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            InnerModel deserializedInnerModel = new InnerModel();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("property".equals(fieldName)) {
                    deserializedInnerModel.property = reader.getString();
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedInnerModel;
        });
    }
}
