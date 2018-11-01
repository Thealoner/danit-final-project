package com.danit.controllers;


import com.danit.models.UserRoles;
import com.danit.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

  @Autowired
  UserRolesRepository userRolesRepository;

  @GetMapping("/test")
  String testRestController() {
    return "success";
  }

  @GetMapping("/test/getroles")
  List<UserRoles> testGetRoles() {
    return userRolesRepository.findAll();
  }

}
