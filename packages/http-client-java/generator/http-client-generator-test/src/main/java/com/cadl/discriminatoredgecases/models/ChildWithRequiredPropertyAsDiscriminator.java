// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.cadl.discriminatoredgecases.models;

import com.azure.core.annotation.Generated;
import com.azure.core.annotation.Immutable;
import com.azure.json.JsonReader;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * The ChildWithRequiredPropertyAsDiscriminator model.
 */
@Immutable
public class ChildWithRequiredPropertyAsDiscriminator extends ParentWithRequiredProperty {
    /*
     * Discriminator property for ChildWithRequiredPropertyAsDiscriminator.
     */
    @Generated
    private String discriminator = "ChildWithRequiredPropertyAsDiscriminator";

    /*
     * The anotherProperty property.
     */
    @Generated
    private final String anotherProperty;

    /**
     * Creates an instance of ChildWithRequiredPropertyAsDiscriminator class.
     * 
     * @param discriminator the discriminator value to set.
     * @param aProperty the aProperty value to set.
     * @param anotherProperty the anotherProperty value to set.
     */
    @Generated
    protected ChildWithRequiredPropertyAsDiscriminator(String discriminator, String aProperty, String anotherProperty) {
        super(discriminator, aProperty);
        this.anotherProperty = anotherProperty;
    }

    /**
     * Get the discriminator property: Discriminator property for ChildWithRequiredPropertyAsDiscriminator.
     * 
     * @return the discriminator value.
     */
    @Generated
    @Override
    public String getDiscriminator() {
        return this.discriminator;
    }

    /**
     * Get the anotherProperty property: The anotherProperty property.
     * 
     * @return the anotherProperty value.
     */
    @Generated
    public String getAnotherProperty() {
        return this.anotherProperty;
    }

    /**
     * {@inheritDoc}
     */
    @Generated
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeStringField("aProperty", getAProperty());
        jsonWriter.writeStringField("anotherProperty", this.anotherProperty);
        jsonWriter.writeStringField("discriminator", this.discriminator);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of ChildWithRequiredPropertyAsDiscriminator from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of ChildWithRequiredPropertyAsDiscriminator if the JsonReader was pointing to an instance of
     * it, or null if it was pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the ChildWithRequiredPropertyAsDiscriminator.
     */
    @Generated
    public static ChildWithRequiredPropertyAsDiscriminator fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            String discriminatorValue = null;
            try (JsonReader readerToUse = reader.bufferObject()) {
                readerToUse.nextToken(); // Prepare for reading
                while (readerToUse.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = readerToUse.getFieldName();
                    readerToUse.nextToken();
                    if ("discriminator".equals(fieldName)) {
                        discriminatorValue = readerToUse.getString();
                        break;
                    } else {
                        readerToUse.skipChildren();
                    }
                }
                // Use the discriminator value to determine which subtype should be deserialized.
                if ("aValue".equals(discriminatorValue)) {
                    return GrandChildWithRequiredProperty.fromJson(readerToUse.reset());
                } else {
                    return fromJsonKnownDiscriminator(readerToUse.reset());
                }
            }
        });
    }

    @Generated
    static ChildWithRequiredPropertyAsDiscriminator fromJsonKnownDiscriminator(JsonReader jsonReader)
        throws IOException {
        return jsonReader.readObject(reader -> {
            String aProperty = null;
            String anotherProperty = null;
            String discriminator = null;
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("aProperty".equals(fieldName)) {
                    aProperty = reader.getString();
                } else if ("anotherProperty".equals(fieldName)) {
                    anotherProperty = reader.getString();
                } else if ("discriminator".equals(fieldName)) {
                    discriminator = reader.getString();
                } else {
                    reader.skipChildren();
                }
            }
            ChildWithRequiredPropertyAsDiscriminator deserializedChildWithRequiredPropertyAsDiscriminator
                = new ChildWithRequiredPropertyAsDiscriminator(discriminator, aProperty, anotherProperty);
            deserializedChildWithRequiredPropertyAsDiscriminator.discriminator = discriminator;

            return deserializedChildWithRequiredPropertyAsDiscriminator;
        });
    }
}