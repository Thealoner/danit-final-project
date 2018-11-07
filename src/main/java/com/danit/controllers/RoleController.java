package com.danit.controllers;


import com.danit.models.UserRoles;
import com.danit.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

  private Logger logger = LoggerFactory.getLogger(RoleController.class);

  @Autowired
  private RoleService roleService;

  @PostMapping("/roles")
  @ResponseStatus(HttpStatus.CREATED)
  //TODO: Should return role?
  public void createRoles(@RequestBody List<UserRoles> roles) {
    logger.info("Adding new roles");
    roleService.saveAllRoles(roles);
    logger.info("Users saved");
  }

  @GetMapping("/roles")
  List<UserRoles> getAllRoles() {
    logger.info("Mapped \"{[/roles],methods=[GET]}\" onto " + new Object() {}
        .getClass()
        .getEnclosingMethod().getName() + "()");
    return roleService.getAllRoles();
  }


}
