package org.highload.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    @EventListener
    public void handleWSConnection(SessionConnectEvent event) {
        System.out.println("New WebSocket connection is being established.");
    }

    @EventListener
    public void handleWSConnected(SessionConnectedEvent event) {
        System.out.println("New WebSocket session has been established.");
    }

    @EventListener
    public void handleWSDisconnect(SessionDisconnectEvent event) {
        System.out.println("A WebSocket session has been disconnected.");
    }

}
