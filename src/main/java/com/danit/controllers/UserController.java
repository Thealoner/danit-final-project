package com.danit.controllers;

import com.danit.models.User;
import com.danit.models.UserRoles;
import com.danit.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  //TODO: Should return user?
  public void createUsers(@RequestBody List<User> users) {
    logger.info("Adding new users");
    userService.saveAllUsers(users);
    logger.info("Users saved");
  }

  @GetMapping("/users/{id}")
  User getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }

  //TODO:Should return user?
  @PutMapping("/users")
  void updateUser(@RequestBody User updUser) {
    userService.updateUser(updUser);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable(name = "id") long id) {
    userService.deleteUserById(id);
  }

  @GetMapping("/users")
  List<User> getAllUsers() {
    logger.info("Mapped \"{[/users],methods=[GET]}\" onto " + new Object() {}
        .getClass()
        .getEnclosingMethod().getName() + "()");
  //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  //    String currentPrincipalName = authentication.getName();
  //    logger.info("User " + currentPrincipalName);
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}/roles")
  Collection<UserRoles> getUserRoles(@PathVariable(name = "id") long id) {
    return userService.getUserById(id).getRoles();
  }

}
