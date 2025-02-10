package org.highload.api_gateway.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfiguration {


    @Value("${application.security.jwt.secret-key:}")
    String secret;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HS256");
        return NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder()))
                )
                .authorizeExchange(authorizeExchangeSpec ->
                        authorizeExchangeSpec
                                .pathMatchers("/swagger-ui/**").permitAll()
                                .pathMatchers("/swagger-ui.html").permitAll()
                                .pathMatchers("/webjars/swagger-ui/**").permitAll()
                                .pathMatchers("/v3/api-docs/**").permitAll()
                                .pathMatchers("/api/auth/**").permitAll()
                                .pathMatchers("/accounts/**").hasAuthority(Scopes.ADMIN.scopeName)
                                .pathMatchers("/banks/**").hasAuthority(Scopes.USER.scopeName)
                                .pathMatchers("/stock/**").hasAuthority(Scopes.USER.scopeName)
                                .anyExchange().denyAll())
                ;
        return http.build();
    }
}

@Configuration
@EnableWebFlux
class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
