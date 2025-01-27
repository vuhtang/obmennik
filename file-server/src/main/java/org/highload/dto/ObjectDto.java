package org.highload.dto;

import io.minio.messages.Item;
import lombok.Data;

@Data
public class ObjectDto {
    private final String id;
    private final String name;
    private final String url;
    private final Long size;

    public static ObjectDto map(Item minioObject, String objectUrl) {
        return new ObjectDto(
                minioObject.etag(),
                minioObject.objectName(),
                objectUrl,
                minioObject.size()
        );
    }
}
