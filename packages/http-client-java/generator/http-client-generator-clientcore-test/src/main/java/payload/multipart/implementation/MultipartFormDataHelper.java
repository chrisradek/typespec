package payload.multipart.implementation;

import io.clientcore.core.http.models.HttpHeaderName;
import io.clientcore.core.http.models.RequestContext;
import io.clientcore.core.models.binarydata.BinaryData;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

// DO NOT modify this helper class

public final class MultipartFormDataHelper {
    /**
     * Line separator for the multipart HTTP request.
     */
    private static final String CRLF = "\r\n";

    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    /**
     * Value to be used as part of the divider for the multipart requests.
     */
    private final String boundary;

    /**
     * The actual part separator in the request. This is obtained by prepending "--" to the "boundary".
     */
    private final String partSeparator;

    /**
     * The marker for the ending of a multipart request. This is obtained by post-pending "--" to the "partSeparator".
     */
    private final String endMarker;

    /**
     * Charset used for encoding the multipart HTTP request.
     */
    private final Charset encoderCharset = StandardCharsets.UTF_8;

    private InputStream requestDataStream = new ByteArrayInputStream(new byte[0]);
    private long requestLength = 0;

    private RequestContext requestContext;
    private BinaryData requestBody;

    /**
     * Default constructor used in the code. The boundary is a random value.
     *
     * @param requestContext the RequestContext to update
     */
    public MultipartFormDataHelper(RequestContext requestContext) {
        this(requestContext, UUID.randomUUID().toString().substring(0, 16));
    }

    private MultipartFormDataHelper(RequestContext requestContext, String boundary) {
        this.requestContext = requestContext;
        this.boundary = boundary;
        this.partSeparator = "--" + boundary;
        this.endMarker = this.partSeparator + "--";
    }

    /**
     * Gets the multipart HTTP request body.
     *
     * @return the BinaryData of the multipart HTTP request body
     */
    public BinaryData getRequestBody() {
        return requestBody;
    }

    // text/plain
    /**
     * Formats a text/plain field for a multipart HTTP request.
     *
     * @param fieldName the field name
     * @param value the value of the text/plain field
     * @return the MultipartFormDataHelper instance
     */
    public MultipartFormDataHelper serializeTextField(String fieldName, String value) {
        if (value != null) {
            String serialized = partSeparator + CRLF + "Content-Disposition: form-data; name=\"" + escapeName(fieldName)
                + "\"" + CRLF + CRLF + value + CRLF;
            byte[] data = serialized.getBytes(encoderCharset);
            appendBytes(data);
        }
        return this;
    }

    // application/json
    /**
     * Formats a application/json field for a multipart HTTP request.
     *
     * @param fieldName the field name
     * @param jsonObject the object of the application/json field
     * @return the MultipartFormDataHelper instance
     */
    public MultipartFormDataHelper serializeJsonField(String fieldName, Object jsonObject) {
        if (jsonObject != null) {
            String serialized
                = partSeparator + CRLF + "Content-Disposition: form-data; name=\"" + escapeName(fieldName) + "\"" + CRLF
                    + "Content-Type: application/json" + CRLF + CRLF + BinaryData.fromObject(jsonObject) + CRLF;
            byte[] data = serialized.getBytes(encoderCharset);
            appendBytes(data);
        }
        return this;
    }

    /**
     * Formats a file field for a multipart HTTP request.
     *
     * @param fieldName the field name
     * @param file the BinaryData of the file
     * @param contentType the content-type of the file
     * @param filename the filename
     * @return the MultipartFormDataHelper instance
     */
    public MultipartFormDataHelper serializeFileField(String fieldName, BinaryData file, String contentType,
        String filename) {
        if (file != null) {
            if (contentType == null || contentType.isEmpty()) {
                contentType = APPLICATION_OCTET_STREAM;
            }
            writeFileField(fieldName, file, contentType, filename);
        }
        return this;
    }

    /**
     * Formats a file field (potentially multiple files) for a multipart HTTP request.
     *
     * @param fieldName the field name
     * @param files the List of BinaryData of the files
     * @param contentTypes the List of content-type of the files
     * @param filenames the List of filenames
     * @return the MultipartFormDataHelper instance
     */
    public MultipartFormDataHelper serializeFileFields(String fieldName, List<BinaryData> files,
        List<String> contentTypes, List<String> filenames) {
        if (files != null) {
            for (int i = 0; i < files.size(); ++i) {
                BinaryData file = files.get(i);
                String contentType = contentTypes.get(i);
                if (contentType == null || contentType.isEmpty()) {
                    contentType = APPLICATION_OCTET_STREAM;
                }
                String filename = filenames.get(i);
                writeFileField(fieldName, file, contentType, filename);
            }
        }
        return this;
    }

    /**
     * Ends the serialization of the multipart HTTP request.
     *
     * @return the MultipartFormDataHelper instance
     */
    public MultipartFormDataHelper end() {
        byte[] data = endMarker.getBytes(encoderCharset);
        appendBytes(data);

        requestBody = BinaryData.fromStream(requestDataStream, requestLength);

        requestContext = requestContext.toBuilder()
            .setHeader(HttpHeaderName.CONTENT_TYPE, "multipart/form-data; boundary=" + this.boundary)
            .setHeader(HttpHeaderName.CONTENT_LENGTH, String.valueOf(requestLength))
            .build();

        return this;
    }

    private void writeFileField(String fieldName, BinaryData file, String contentType, String filename) {
        String contentDispositionFilename = "";
        if (filename != null && !filename.isEmpty()) {
            contentDispositionFilename = "; filename=\"" + escapeName(filename) + "\"";
        }

        // Multipart preamble
        String fileFieldPreamble
            = partSeparator + CRLF + "Content-Disposition: form-data; name=\"" + escapeName(fieldName) + "\""
                + contentDispositionFilename + CRLF + "Content-Type: " + contentType + CRLF + CRLF;
        byte[] data = fileFieldPreamble.getBytes(encoderCharset);
        appendBytes(data);

        // Writing the file into the request as a byte stream
        requestLength += file.getLength();
        requestDataStream = new SequenceInputStream(requestDataStream, file.toStream());

        // CRLF
        data = CRLF.getBytes(encoderCharset);
        appendBytes(data);
    }

    private void appendBytes(byte[] bytes) {
        requestLength += bytes.length;
        requestDataStream = new SequenceInputStream(requestDataStream, new ByteArrayInputStream(bytes));
    }

    private static String escapeName(String name) {
        return name.replace("\n", "%0A").replace("\r", "%0D").replace("\"", "%22");
    }
}
