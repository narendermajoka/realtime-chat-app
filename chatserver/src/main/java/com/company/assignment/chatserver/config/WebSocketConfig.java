package com.company.assignment.chatserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${rabbit.mq.broker.host}")
    private String rabbitMqHost;
    @Value("${rabbit.mq.broker.port}")
    private Integer port;
    @Value("${rabbit.mq.broker.user}")
    private String rabbitMqUser;
    @Value("${rabbit.mq.broker.password}")
    private String rabbitMqPassword;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost(rabbitMqHost)
                .setRelayPort(port)
                .setSystemLogin(rabbitMqUser)
                .setSystemPasscode(rabbitMqPassword)
                .setClientLogin(rabbitMqUser)
                .setClientPasscode(rabbitMqPassword);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
