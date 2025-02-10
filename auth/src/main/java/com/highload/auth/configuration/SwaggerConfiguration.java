package com.highload.auth.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
            title = "auth",
            description = "auth service",
            version = "1.0.0"
    )
)
@Configuration
public class SwaggerConfiguration {
}
