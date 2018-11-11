package com.danit.controllers;

import com.danit.models.User;
import com.danit.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.danit.utils.SpringSecurityUtils.getCurrentPrincipalName;

@RestController
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  List<User> createUsers(@RequestBody List<User> users) {
    logger.info("User " + getCurrentPrincipalName() + " is saving new users: " + users);
    return userService.saveUsers(users);
  }

  @GetMapping("/users")
  List<User> getAllUsers() {
    logger.info("User " + getCurrentPrincipalName() + " got all users data");
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  User getUserById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " got user data with id: " + id);
    return userService.getUserById(id);
  }

  @PutMapping("/users")
  void updateUser(@RequestBody List<User> users) {
    logger.info("User " + getCurrentPrincipalName() + " is updating users data: " + users);
    userService.updateUsers(users);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable(name = "id") long id) {
    logger.info("User " + getCurrentPrincipalName() + " try to delete user with id: " + id);
    userService.deleteUserById(id);
  }

  @DeleteMapping("/users")
  public void deleteUsers(@RequestBody List<User> users) {
    logger.info("User " + getCurrentPrincipalName() + " is trying to delete users: " + users);
    userService.deleteUsers(users);
  }

}
