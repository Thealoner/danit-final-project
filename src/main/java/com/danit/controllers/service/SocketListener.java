package com.danit.controllers.service;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SocketListener {

  @MessageMapping("/hello")
  void tabOpened() {

  }

}
