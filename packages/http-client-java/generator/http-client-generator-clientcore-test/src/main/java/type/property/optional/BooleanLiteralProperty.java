package type.property.optional;

import io.clientcore.core.annotations.Metadata;
import io.clientcore.core.annotations.MetadataProperties;
import io.clientcore.core.serialization.json.JsonReader;
import io.clientcore.core.serialization.json.JsonSerializable;
import io.clientcore.core.serialization.json.JsonToken;
import io.clientcore.core.serialization.json.JsonWriter;
import java.io.IOException;

/**
 * Model with boolean literal property.
 */
@Metadata(properties = { MetadataProperties.FLUENT })
public final class BooleanLiteralProperty implements JsonSerializable<BooleanLiteralProperty> {
    /*
     * Property
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    private BooleanLiteralPropertyProperty property;

    /**
     * Creates an instance of BooleanLiteralProperty class.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public BooleanLiteralProperty() {
    }

    /**
     * Get the property property: Property.
     * 
     * @return the property value.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public BooleanLiteralPropertyProperty getProperty() {
        return this.property;
    }

    /**
     * Set the property property: Property.
     * 
     * @param property the property value to set.
     * @return the BooleanLiteralProperty object itself.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public BooleanLiteralProperty setProperty(BooleanLiteralPropertyProperty property) {
        this.property = property;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeBooleanField("property", this.property == null ? null : this.property.toBoolean());
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of BooleanLiteralProperty from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of BooleanLiteralProperty if the JsonReader was pointing to an instance of it, or null if it
     * was pointing to JSON null.
     * @throws IOException If an error occurs while reading the BooleanLiteralProperty.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public static BooleanLiteralProperty fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            BooleanLiteralProperty deserializedBooleanLiteralProperty = new BooleanLiteralProperty();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("property".equals(fieldName)) {
                    deserializedBooleanLiteralProperty.property
                        = BooleanLiteralPropertyProperty.fromBoolean(reader.getBoolean());
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedBooleanLiteralProperty;
        });
    }
}
