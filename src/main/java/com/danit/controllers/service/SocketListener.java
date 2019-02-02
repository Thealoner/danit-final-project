package com.danit.controllers.service;

import com.danit.models.service.Tab;
import com.danit.services.tabs.TabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class SocketListener {

  private TabService tabService;

  public SocketListener(TabService tabService) {
    this.tabService = tabService;
  }

  @MessageMapping("/tab/open")
  @SendTo("/events/{userId}")
  Tab tabOpened(@DestinationVariable String fleetId, Tab tab, Principal principal) {
    log.info("tab opened =" + tab);
    return tabService.checkIfTabIsUsed(tabService.saveTab(tab));
  }

  @MessageMapping("/tab/close, Principal principal")
  void tabClosed(Tab tab) {
    log.info("tab closed =" + tab);
    tabService.deleteTab(tab);
  }

}
