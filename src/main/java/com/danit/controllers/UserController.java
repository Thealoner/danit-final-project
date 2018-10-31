package com.danit.controllers;

import com.danit.models.User;
import com.danit.models.UserRoles;
import com.danit.models.UserRolesEnum;
import com.danit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Secured("ADMIN")
  @CrossOrigin(origins = "http://localhost:3000")
  @PutMapping("/users")
  public void addUser(@RequestParam(value = "username") String userName,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "role") List<UserRoles> roles) {
    User user = new User(userName, password, roles);
    userService.saveUser(user);
  }

  @Secured("ADMIN")
  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/users")
  List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @Secured("ADMIN")
  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/users/{id}")
  Optional<User> getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }
}
