package com.danit.controllers;

import com.danit.models.Role;
import com.danit.models.User;
import com.danit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/api/users/signup")
    public void signUp(@RequestBody User user) {
        userService.saveUser(user);
    }

    @GetMapping("/api/users/adduser")
    public void addUser(@RequestParam(value = "username") String userName,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "role") String role) {
        User user = new User(userName, bCryptPasswordEncoder.encode(password), Arrays.asList(
                new Role(role)));
        userService.saveUser(user);
    }

    @GetMapping("/api/users/getallusers")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

   @GetMapping("/api/user/get/{id}")
   Optional<User> getUserById(@PathVariable(name = "id") long id) {
        return userService.getUserById(id);
    }
}
