package type.property.valuetypes;

import io.clientcore.core.annotations.Metadata;
import io.clientcore.core.annotations.MetadataProperties;
import io.clientcore.core.serialization.json.JsonReader;
import io.clientcore.core.serialization.json.JsonSerializable;
import io.clientcore.core.serialization.json.JsonToken;
import io.clientcore.core.serialization.json.JsonWriter;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Model with a decimal property.
 */
@Metadata(properties = { MetadataProperties.IMMUTABLE })
public final class DecimalProperty implements JsonSerializable<DecimalProperty> {
    /*
     * Property
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    private final BigDecimal property;

    /**
     * Creates an instance of DecimalProperty class.
     * 
     * @param property the property value to set.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public DecimalProperty(BigDecimal property) {
        this.property = property;
    }

    /**
     * Get the property property: Property.
     * 
     * @return the property value.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public BigDecimal getProperty() {
        return this.property;
    }

    /**
     * {@inheritDoc}
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeNumberField("property", this.property);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of DecimalProperty from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of DecimalProperty if the JsonReader was pointing to an instance of it, or null if it was
     * pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the DecimalProperty.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public static DecimalProperty fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            BigDecimal property = null;
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("property".equals(fieldName)) {
                    property = reader.getNullable(nonNullReader -> new BigDecimal(nonNullReader.getString()));
                } else {
                    reader.skipChildren();
                }
            }
            return new DecimalProperty(property);
        });
    }
}
