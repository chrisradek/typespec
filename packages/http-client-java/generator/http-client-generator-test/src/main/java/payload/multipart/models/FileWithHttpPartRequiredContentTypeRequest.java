// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package payload.multipart.models;

import com.azure.core.annotation.Generated;
import com.azure.core.annotation.Immutable;

/**
 * The FileWithHttpPartRequiredContentTypeRequest model.
 */
@Immutable
public final class FileWithHttpPartRequiredContentTypeRequest {
    /*
     * The profileImage property.
     */
    @Generated
    private final FileRequiredMetaData profileImage;

    /**
     * Creates an instance of FileWithHttpPartRequiredContentTypeRequest class.
     * 
     * @param profileImage the profileImage value to set.
     */
    @Generated
    public FileWithHttpPartRequiredContentTypeRequest(FileRequiredMetaData profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * Get the profileImage property: The profileImage property.
     * 
     * @return the profileImage value.
     */
    @Generated
    public FileRequiredMetaData getProfileImage() {
        return this.profileImage;
    }
}
