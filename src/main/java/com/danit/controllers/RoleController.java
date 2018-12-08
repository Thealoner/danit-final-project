package com.danit.controllers;


import com.danit.models.UserRoles;
import com.danit.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

  private Logger logger = LoggerFactory.getLogger(RoleController.class);

  private RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping
  public void createRoles(@RequestBody List<UserRoles> roles, Principal principal) {
    logger.info(principal.getName() + " is saving new roles: " + roles);
    roleService.saveAllRoles(roles);
  }

  @GetMapping
  List<UserRoles> getAllRoles(Principal principal) {
    logger.info(principal.getName() + " got all user roles data");
    return roleService.getAllRoles();
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  void deleteRole(@RequestBody List<UserRoles> roles, Principal principal) {
    logger.info(principal.getName() + " is trying to delete roles: " + roles);
    roleService.deleteRoles(roles);
  }

}
