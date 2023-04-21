package com.ggh_dev.AroundBook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8080")
                .withSockJS();  //WebSocket 핸드셰이크 커넥션 생성 경로
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //어플리케이션 내부에서 사용할 path 지정
        registry.enableSimpleBroker("/sub");//구독 중인 Client 에게 메세지 전달

        registry.setApplicationDestinationPrefixes("/pub");//Client 의 Send 요청 처리
    }
}
