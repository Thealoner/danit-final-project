package com.danit.controllers.service;

import com.danit.models.service.Tab;
import com.danit.services.tabs.TabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Slf4j
@Controller
public class SocketListener {

  private TabService tabService;

  private SimpMessageSendingOperations messagingTemplate;

  public SocketListener(TabService tabService, SimpMessageSendingOperations messagingTemplate) {
    this.tabService = tabService;
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("/tab/open")
  void tabOpened(Tab tab, Principal principal) {
    log.info("tab opened =" + tab);
    Tab savedTab = tabService.saveTab(tab);
    Tab tab1 = tabService.checkIfTabIsUsed(savedTab);
    messagingTemplate.convertAndSend("/events/1",
        savedTab);
  }

  @MessageMapping("/tab/close, Principal principal")
  void tabClosed(Tab tab) {
    log.info("tab closed =" + tab);
    tabService.deleteTab(tab);
  }

}
