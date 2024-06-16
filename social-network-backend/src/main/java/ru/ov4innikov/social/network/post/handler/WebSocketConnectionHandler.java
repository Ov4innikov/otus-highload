package ru.ov4innikov.social.network.post.handler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.ov4innikov.social.network.user.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketConnectionHandler extends TextWebSocketHandler {

    private final UserService userService;

    Map<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();


    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        Principal principal = session.getPrincipal();
        log.trace("{} Connected {}",  principal, session.getId());
        super.afterConnectionEstablished(session);
        log.trace("Connected");

        webSocketSessions.put(principal.getName(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      @NotNull CloseStatus status) throws Exception {
        Principal principal = session.getPrincipal();
        log.trace("{} DisConnected {}", principal, session.getId());
        super.afterConnectionClosed(session, status);
        log.trace("DisConnected");

        webSocketSessions.remove(principal.getName());
    }

    @Override
    public void handleMessage(@NotNull WebSocketSession session,
                              @NotNull WebSocketMessage<?> message)
            throws Exception {
        log.trace("start handleMessage {}", message);
        super.handleMessage(session, message);
        log.trace("end handleMessage");
    }

    public void sendMessageAll(String message) {
        for (WebSocketSession webSocketSession : webSocketSessions.values()) {
            log.trace("Send to {} message {}", webSocketSession, message);
            try {
                webSocketSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    public void sendToUser(String user, String message) {
        WebSocketSession webSocketSession = webSocketSessions.get(user);
        if (webSocketSession == null) {
            log.trace("Not found user {}", user);
            return;
        }
        webSocketSession.sendMessage(new TextMessage(message));
    }
}