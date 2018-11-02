package com.danit.controllers;

import com.danit.models.User;
import com.danit.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @PutMapping("/users")
  public void addUser(@RequestBody User user) {
    userService.saveUser(user);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable(name = "id") long id) {
    userService.deleteUserById(id);
  }

  @GetMapping("/users")
  List<User> getAllUsers() {
    logger.info("Mapped \"{[/users],methods=[GET]}\" onto com.danit.controllers.UserController.getAllUsers()");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    logger.info("User " + currentPrincipalName);
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  Optional<User> getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }
}
