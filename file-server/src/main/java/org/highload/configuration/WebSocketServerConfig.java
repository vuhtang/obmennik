package org.highload.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketServerConfig implements WebSocketMessageBrokerConfigurer {
//    @Value("${websocket.s3-topic}")
//    private final String topic;
//
//    @Value("${websocket.s3-url}")
//    private final String urlPrefix;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/storage-ws")
                .setAllowedOriginPatterns("*");
//                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue", "/file");
//        registry.enableStompBrokerRelay("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("app");
        registry.setUserDestinationPrefix("/user");
        registry.setPathMatcher(new AntPathMatcher("."));
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(4 * 8192);
        registry.setTimeToFirstMessage(30000);
    }

//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        messageConverters.add(MappingJackson2MessageConverter());
//        return true;
//    }

//    override fun configureWebSocketTransport(registration:WebSocketTransportRegistration) {
//        registration.setMessageSizeLimit(50 * 1024 * 1024)
//    }


//    @Bean
//    fun createWebSocketContainer(): ServletServerContainerFactoryBean {
//        val container = ServletServerContainerFactoryBean()
//        container.setMaxTextMessageBufferSize(1024 * 1024 * 20)
//        container.setMaxBinaryMessageBufferSize(1024 * 1024 * 20)
//        container.setMaxSessionIdleTimeout(15 * 60000L)
//        return container
//    }
}
