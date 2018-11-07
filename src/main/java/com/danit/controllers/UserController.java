package com.danit.controllers;

import com.danit.models.User;
import com.danit.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public void createUsers(@RequestBody List<User> users) {
    logger.info("Adding new users");
    userService.saveUsers(users);
    logger.info("Users saved");
  }

  @GetMapping("/users/{id}")
  User getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }

  @PutMapping("/users")
  void updateUser(@RequestBody List<User> users) {
    userService.updateUsers(users);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable(name = "id") long id) {
    userService.deleteUserById(id);
  }

  @GetMapping("/users")
  List<User> getAllUsers() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    logger.info("User " + currentPrincipalName);
    return userService.getAllUsers();
  }

  @DeleteMapping("/users")
  public void deleteUsers(@RequestBody List<User> users) {
    userService.deleteUsers(users);
  }

}
