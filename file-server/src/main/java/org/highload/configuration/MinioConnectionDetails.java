package org.highload.configuration;

import lombok.Data;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "storage")
public class MinioConnectionDetails implements ConnectionDetails {
    protected final String url;

    protected final String accessKey;

    protected final String secretKey;

    protected final String bucket;
}
