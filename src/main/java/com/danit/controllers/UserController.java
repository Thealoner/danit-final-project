package com.danit.controllers;

import com.danit.dto.UserDto;
import com.danit.dto.service.UserListRequestDto;
import com.danit.facades.UserFacade;
import com.danit.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static com.danit.utils.ControllerUtils.convertToMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  private UserFacade userFacade;

  public UserController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @PostMapping
  public ResponseEntity<List<UserDto>> createUsers(@RequestBody List<User> users, Principal principal) {
    log.info(principal.getName() + " is saving new users: " + users);
    return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.saveEntities(users));
  }

  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllUsers(Principal principal,
                                                  Pageable pageable,
                                                  UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + " got all users data");
    return ResponseEntity.ok(convertToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got user data with id: " + id);
    return ResponseEntity.ok(userFacade.getEntityById(id));
  }

  @PutMapping
  public ResponseEntity<List<UserDto>> updateUser(@RequestBody List<User> users, Principal principal) {
    log.info(principal.getName() + " is updating users data: " + users);
    return ResponseEntity.ok(userFacade.updateEntities(users));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUserById(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " try to delete user with id: " + id);
    userFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteUsers(@RequestBody List<User> users, Principal principal) {
    log.info(principal.getName() + " is trying to delete users: " + users);
    userFacade.deleteEntities(users);
  }
}
