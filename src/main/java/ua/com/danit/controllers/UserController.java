package ua.com.danit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.danit.models.User;
import ua.com.danit.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/users")
  List<User> createUsers(@RequestBody List<User> users, Principal principal) {
    logger.info(principal.getName() + " is saving new users: " + users);
    return userService.saveUsers(users);
  }

  @GetMapping("/users")
  List<User> getAllUsers(Principal principal) {
    logger.info(principal.getName() + " got all users data");
    return userService.getAllUsers();
  }

  @GetMapping("/users/{id}")
  User getUserById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " got user data with id: " + id);
    return userService.getUserById(id);
  }

  @PutMapping("/users")
  List<User> updateUser(@RequestBody List<User> users, Principal principal) {
    logger.info(principal.getName() + " is updating users data: " + users);
    return userService.updateUsers(users);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUserById(@PathVariable(name = "id") long id, Principal principal) {
    logger.info(principal.getName() + " try to delete user with id: " + id);
    userService.deleteUserById(id);
  }

  @DeleteMapping("/users")
  public void deleteUsers(@RequestBody List<User> users, Principal principal) {
    logger.info(principal.getName() + " is trying to delete users: " + users);
    userService.deleteUsers(users);
  }

}
