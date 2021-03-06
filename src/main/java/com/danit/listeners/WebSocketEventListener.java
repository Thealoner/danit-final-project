package com.danit.listeners;

import com.danit.services.UserService;
import com.danit.services.tabs.TabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Objects;

@Slf4j
@Component
public class WebSocketEventListener {

  private UserService userService;

  private TabService tabService;

  public WebSocketEventListener(UserService userService, TabService tabService) {
    this.userService = userService;
    this.tabService = tabService;
  }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    if (Objects.nonNull(event.getUser())) {
      log.info("Received a new web socket connection for userName=" + (event.getUser()).getName());
    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    Object user = headerAccessor.getHeader("simpUser");
    if (Objects.nonNull(user)) {
      String username = ((Principal) user).getName();
      log.info("User disconnected userName=" + username);
      tabService.deleteAllUserTabs(
          userService.findUserByUsername(username).getId());
    }
  }
}
