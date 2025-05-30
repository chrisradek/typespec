// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package tsptest.armresourceprovider.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.json.JsonReader;
import com.azure.json.JsonSerializable;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * The updatable properties of the TopLevelArmResource.
 */
@Fluent
public final class TopLevelArmResourceUpdateProperties
    implements JsonSerializable<TopLevelArmResourceUpdateProperties> {
    /*
     * The userName property.
     */
    private String userName;

    /*
     * The userNames property.
     */
    private String userNames;

    /*
     * The accuserName property.
     */
    private String accuserName;

    /**
     * Creates an instance of TopLevelArmResourceUpdateProperties class.
     */
    public TopLevelArmResourceUpdateProperties() {
    }

    /**
     * Get the userName property: The userName property.
     * 
     * @return the userName value.
     */
    public String userName() {
        return this.userName;
    }

    /**
     * Set the userName property: The userName property.
     * 
     * @param userName the userName value to set.
     * @return the TopLevelArmResourceUpdateProperties object itself.
     */
    public TopLevelArmResourceUpdateProperties withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Get the userNames property: The userNames property.
     * 
     * @return the userNames value.
     */
    public String userNames() {
        return this.userNames;
    }

    /**
     * Set the userNames property: The userNames property.
     * 
     * @param userNames the userNames value to set.
     * @return the TopLevelArmResourceUpdateProperties object itself.
     */
    public TopLevelArmResourceUpdateProperties withUserNames(String userNames) {
        this.userNames = userNames;
        return this;
    }

    /**
     * Get the accuserName property: The accuserName property.
     * 
     * @return the accuserName value.
     */
    public String accuserName() {
        return this.accuserName;
    }

    /**
     * Set the accuserName property: The accuserName property.
     * 
     * @param accuserName the accuserName value to set.
     * @return the TopLevelArmResourceUpdateProperties object itself.
     */
    public TopLevelArmResourceUpdateProperties withAccuserName(String accuserName) {
        this.accuserName = accuserName;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeStringField("userName", this.userName);
        jsonWriter.writeStringField("userNames", this.userNames);
        jsonWriter.writeStringField("accuserName", this.accuserName);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of TopLevelArmResourceUpdateProperties from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of TopLevelArmResourceUpdateProperties if the JsonReader was pointing to an instance of it,
     * or null if it was pointing to JSON null.
     * @throws IOException If an error occurs while reading the TopLevelArmResourceUpdateProperties.
     */
    public static TopLevelArmResourceUpdateProperties fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            TopLevelArmResourceUpdateProperties deserializedTopLevelArmResourceUpdateProperties
                = new TopLevelArmResourceUpdateProperties();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("userName".equals(fieldName)) {
                    deserializedTopLevelArmResourceUpdateProperties.userName = reader.getString();
                } else if ("userNames".equals(fieldName)) {
                    deserializedTopLevelArmResourceUpdateProperties.userNames = reader.getString();
                } else if ("accuserName".equals(fieldName)) {
                    deserializedTopLevelArmResourceUpdateProperties.accuserName = reader.getString();
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedTopLevelArmResourceUpdateProperties;
        });
    }
}
