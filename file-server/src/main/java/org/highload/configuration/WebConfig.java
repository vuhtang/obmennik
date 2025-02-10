package org.highload.configuration;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new AbstractHttpMessageConverter<InputStream>(MediaType.APPLICATION_OCTET_STREAM) {
            protected boolean supports(@NotNull Class<?> clazz) {
                return InputStream.class.isAssignableFrom(clazz);
            }

            @NotNull
            protected InputStream readInternal(@NotNull Class<? extends InputStream> clazz, @NotNull HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                return inputMessage.getBody();
            }

            protected void writeInternal(@NotNull InputStream inputStream, @NotNull HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                IOUtils.copy(inputStream, outputMessage.getBody());
            }
        });
        addDefaultHttpMessageConverters(converters);
        super.configureMessageConverters(converters);
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
