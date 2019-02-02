package com.danit.listeners;

import com.danit.services.tabs.TabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Slf4j
@Component
public class WebSocketEventListener {

  private SimpMessageSendingOperations messagingTemplate;

  private TabService tabService;

  public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate,
                                TabService tabService) {
    this.messagingTemplate = messagingTemplate;
    this.tabService = tabService;
  }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("Received a new web socket connection for userName=" + event.getUser().getName());
    //messagingTemplate.convertAndSend("/events/get", "new user connected...");
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");
    log.info("User disconnected userName=" + username);
    tabService.deleteAllUserTabs(username);
    //messagingTemplate.convertAndSend("/events/users", "disconnected user: " + username);
  }
}
