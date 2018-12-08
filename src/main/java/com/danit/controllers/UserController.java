package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.UserListRequestDto;
import com.danit.facades.UserFacade;
import com.danit.models.User;
import com.fasterxml.jackson.annotation.JsonView;
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

import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all users data";
  private UserFacade userFacade;

  public UserController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createUsersDto(@RequestBody List<User> users,
                                                         Principal principal) {
    log.info(principal.getName() + " is saving new users: " + users);
    return ResponseEntity.ok(convertDtoToMap(userFacade.saveEntities(users)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllUsersDtoIds(Principal principal,
                                                                 Pageable pageable,
                                                                 UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllUsersDtoShort(Principal principal,
                                                                 Pageable pageable,
                                                                 UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllUsersDtoExtended(Principal principal,
                                                  Pageable pageable,
                                                  UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getUserByIdDto(@PathVariable(name = "id") long id, Principal principal) {
    log.info(principal.getName() + " got user data with id: " + id);
    return ResponseEntity.ok(convertDtoToMap(userFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<Map<String, Object>> updateUsersDto(@RequestBody List<User> users, Principal principal) {
    log.info(principal.getName() + " is updating users data: " + users);
    return ResponseEntity.ok(convertDtoToMap(userFacade.updateEntities(users)));
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
