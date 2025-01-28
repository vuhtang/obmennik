package org.highload.api_gateway.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@ConfigurationProperties("application.gateway")
//class GatewayProperties {
//    @Value("${application.gateway.auth}")
//    String auth;
//    String banking;
//    String stock;
//    String account;
//}

@RequiredArgsConstructor
@Configuration
//@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayConfiguration {
//    private final GatewayProperties gatewayProperties;

    @Value("${application.gateway.auth}")
    String auth;

    @Value("${application.gateway.banking}")
    String banking;

    @Value("${application.gateway.stock}")
    String stock;

    @Value("${application.gateway.accounts}")
    String accounts;

    @Bean
    RouteLocator gatewayRoute(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()
                .route("auth", route -> route
                        .path("/api/auth/**")
                        .uri(auth))
                .route("banking", route -> route
                        .path("/banks/**")
                        .uri(banking))
                .route("stock", route -> route
                        .path("/stock/**")
                        .uri(stock))
                .route("accounts", route -> route
                        .path("/accounts/**")
                        .uri(accounts))
                .build();
    }
}
