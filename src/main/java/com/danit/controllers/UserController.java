package com.danit.controllers;

import com.danit.models.User;
import com.danit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  Optional<User> getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }
}
