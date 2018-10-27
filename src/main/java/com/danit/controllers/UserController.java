package com.danit.controllers;

import com.danit.models.Role;
import com.danit.models.User;
import com.danit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  //@CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/api/users/auth")
  public void auth() {
    System.out.println("post user:");
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/api/users/add")
  public void addUser(@RequestParam(value = "username") String userName,
                      @RequestParam(value = "password") String password,
                      @RequestParam(value = "role") String role) {
    User user = new User(userName, bCryptPasswordEncoder.encode(password), Arrays.asList(
        new Role(role)));
    userService.saveUser(user);
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/api/users/get/all")
  List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @GetMapping("/api/users/get/{id}")
  Optional<User> getUserById(@PathVariable(name = "id") long id) {
    return userService.getUserById(id);
  }
}
