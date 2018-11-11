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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class RoleController {

  private Logger logger = LoggerFactory.getLogger(RoleController.class);

  private RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  @PostMapping("/roles")
  @ResponseStatus(HttpStatus.CREATED)
  public void createRoles(@RequestBody List<UserRoles> roles) {
    logger.info(getCurrentPrincipalName() + " is saving new roles: " + roles);
    roleService.saveAllRoles(roles);
  }

  @GetMapping("/roles")
  List<UserRoles> getAllRoles() {
    logger.info(getCurrentPrincipalName() + " got all user roles data");
    return roleService.getAllRoles();
  }

  @DeleteMapping("/roles")
  @ResponseStatus(HttpStatus.OK)
  void deleteRole(@RequestBody List<UserRoles> roles) {
    logger.info(getCurrentPrincipalName() + " is trying to delete roles: " + roles);
    roleService.deleteRoles(roles);
  }

}
