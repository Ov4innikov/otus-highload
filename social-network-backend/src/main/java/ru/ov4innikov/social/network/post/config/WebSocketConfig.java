package ru.ov4innikov.social.network.post.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.*;
import ru.ov4innikov.social.network.post.handler.WebSocketConnectionHandler;

import static org.springframework.messaging.simp.SimpMessageType.*;

@Log4j2
@RequiredArgsConstructor
@EnableWebSocket
@EnableWebSocketSecurity
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketConnectionHandler webSocketConnectionHandler;


    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
                .anyMessage().authenticated()
                .nullDestMatcher().permitAll()
                .simpSubscribeDestMatchers("**").authenticated()
                .simpDestMatchers("**").authenticated()
                .simpSubscribeDestMatchers("**").authenticated()
                .simpTypeMatchers(CONNECT, CONNECT_ACK, MESSAGE, SUBSCRIBE).authenticated()
                .anyMessage().authenticated();

        return messages.build();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry)
    {
        webSocketHandlerRegistry
                .addHandler(webSocketConnectionHandler,"/post/feed/posted")
                .setAllowedOrigins("*");
    }
}
