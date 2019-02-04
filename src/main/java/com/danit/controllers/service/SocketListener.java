package com.danit.controllers.service;

import com.danit.models.service.Tab;
import com.danit.services.UserService;
import com.danit.services.tabs.TabService;
import com.danit.utils.WebSocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class SocketListener {

  private TabService tabService;

  private UserService userService;

  private WebSocketUtils webSocketUtils;

  private SimpMessageSendingOperations messagingTemplate;

  @Autowired
  public SocketListener(TabService tabService, UserService userService,
                        WebSocketUtils webSocketUtils, SimpMessageSendingOperations messagingTemplate) {
    this.tabService = tabService;
    this.userService = userService;
    this.webSocketUtils = webSocketUtils;
    this.messagingTemplate = messagingTemplate;
  }

  @MessageMapping("/tab/open")
  void tabOpened(Tab tab, Principal principal) {
    tab = tabService.saveTab(tab);
    log.info("tab is opened =" + tab);
    Long userId = userService.findUserByUsername(principal.getName()).getId();
    messagingTemplate.convertAndSend(webSocketUtils.getPrefix() + userId,
        tabService.checkIfTabIsUsed(tab));
  }

  @MessageMapping("/tab/close")
  void tabClosed(Tab tab, Principal principal) {
    log.info("tab is closed =" + tab);
    tabService.deleteTab(tab);
    tabService.getAllTabOwnersByTab(tab)
        .forEach(tab1 -> messagingTemplate.convertAndSend(webSocketUtils.getPrefix() + tab1.getUserId(), tab1));
  }

  @MessageMapping("/tab/check")
  void tabCheck(Tab tab, Principal principal) {
    log.info("tab is checked =" + tab);
    Long userId = userService.findUserByUsername(principal.getName()).getId();
    messagingTemplate.convertAndSend(webSocketUtils.getPrefix() + userId,
        tabService.checkIfTabIsUsed(tab));
  }

  @MessageMapping("/tabs/check")
  void tabsCheck(List<Tab> tabs, Principal principal) {
    log.info("tabs are checked =" + tabs);
    Long userId = userService.findUserByUsername(principal.getName()).getId();
    messagingTemplate.convertAndSend(webSocketUtils.getPrefix() + userId,
        tabService.checkIfTabsIsUsed(tabs));
  }

}
