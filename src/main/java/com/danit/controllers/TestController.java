package com.danit.controllers;


import com.danit.models.UserRoles;
import com.danit.repositories.UserRolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

  Logger logger = LoggerFactory.getLogger(TestController.class);


  @Autowired
  UserRolesRepository userRolesRepository;

  @GetMapping("/test")
  String testRestController() {
    logger.info("Mapped \"{[/test],methods=[GET]}\" onto com.danit.controllers.TestController.testRestController()");
    return "success";
  }

  @GetMapping("/test/getroles")
  List<UserRoles> testGetRoles() {
    return userRolesRepository.findAll();
  }

}
