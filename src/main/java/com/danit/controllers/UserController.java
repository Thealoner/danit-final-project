package com.danit.controllers;

import com.danit.dto.Views;
import com.danit.dto.service.UserListRequestDto;
import com.danit.facades.UserFacade;
import com.danit.facades.UserRoleFacade;
import com.danit.models.User;
import com.danit.models.UserRole;
import com.danit.services.UserRoleService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.function.Consumer;

import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_NUMBER;
import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_SIZE;
import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  private static final String LOG_MSG_GOT_ALL_DATA = " got all users data";
  private UserFacade userFacade;
  private UserRoleService roleService;
  private UserRoleFacade userRoleFacade;
  private BCryptPasswordEncoder bcryptPasswordEncoder;

  public UserController(UserFacade userFacade, UserRoleService roleService, UserRoleFacade userRoleFacade,
                        BCryptPasswordEncoder bcryptPasswordEncoder) {
    this.userFacade = userFacade;
    this.roleService = roleService;
    this.userRoleFacade = userRoleFacade;
    this.bcryptPasswordEncoder = bcryptPasswordEncoder;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createUsersDto(@RequestBody List<User> users,
                                                            Principal principal) {
    log.info(principal.getName() + " is saving new users: " + users);
    users.forEach(user -> user.setPassword(bcryptPasswordEncoder.encode(user.getPassword())));
    return ResponseEntity.ok(convertDtoToMap(userFacade.saveEntities(users)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllUsersDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllUsersDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    return ResponseEntity.ok(convertPageToMap(userFacade.getAllEntities(userListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllUsersDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserListRequestDto userListRequestDto) {
    log.info(principal.getName() + LOG_MSG_GOT_ALL_DATA);
    log.info("data " + userFacade.getAllEntities(userListRequestDto, pageable).getContent());
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

  //----related entities methods----------------------------------------------------------------------------------------

  @PutMapping("/{userId}/role/{roleId}")
  @JsonView(Views.Extended.class)
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignRoleToUser(@PathVariable(name = "userId") Long userId,
                                                             @PathVariable(name = "roleId") Long roleId,
                                                             Principal principal) {
    log.info(principal.getName() + " is trying to assign roleId=" + roleId + " to userId = " + userId);
    roleService.assignRoleToUser(userId, roleId);
    return ResponseEntity.ok(convertDtoToMap(userFacade.getEntityById(userId)));
  }

  @PutMapping("/{userId}/roles")
  @JsonView(Views.Extended.class)
  @ResponseStatus(HttpStatus.OK)
  ResponseEntity<Map<String, Object>> assignRolesToUser(@PathVariable(name = "userId") Long userId,
                                                        @RequestBody List<UserRole> roles,
                                                       Principal principal) {
    log.info(principal.getName() + " is trying to assign roles " + roles + " to userId = " + userId);
    roleService.assignRolesToUser(userId, roles);
    return ResponseEntity.ok(convertDtoToMap(userFacade.getEntityById(userId)));
  }

  @DeleteMapping("/{userId}/role/{roleId}")
  @JsonView(Views.Extended.class)
  @ResponseStatus(HttpStatus.OK)
  void deleteRoleFromUser(@PathVariable(name = "userId") Long userId,
                                                       @PathVariable(name = "roleId") Long roleId,
                                                       Principal principal) {
    log.info(principal.getName() + " is trying to delet roleId=" + roleId + " from userId = " + userId);
    roleService.deleteRoleFromUser(userId, roleId);
  }

  @DeleteMapping("/{userId}/roles")
  @JsonView(Views.Extended.class)
  @ResponseStatus(HttpStatus.OK)
  void deleteRolesFromUser(@PathVariable(name = "userId") Long userId,
                                                        @RequestBody List<UserRole> roles,
                                                        Principal principal) {
    log.info(principal.getName() + " is trying to delete roles " + roles + " from userId = " + userId);
    roleService.deleteRolesFromUser(userId, roles);
  }

  @JsonView(Views.Ids.class)
  @GetMapping("/{userId}/roles/ids")
  ResponseEntity<Map<String, Object>> getAllRolesOfUserIds(
      @PathVariable(name = "userId")
          long id,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal) {
    log.info(principal.getName() + " got roles of user with id: " + id);
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.findAllRolesDtoForUserId(id, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("/{userId}/roles/short")
  ResponseEntity<Map<String, Object>> getAllRolesOfUserShort(
      @PathVariable(name = "userId")
          long id,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal) {
    log.info(principal.getName() + " got roles of user with id: " + id);
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.findAllRolesDtoForUserId(id, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{userId}/roles")
  ResponseEntity<Map<String, Object>> getAllRolesOfUser(
      @PathVariable(name = "userId")
          long id,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal) {
    log.info(principal.getName() + " got roles of user with id: " + id);
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.findAllRolesDtoForUserId(id, pageable)));
  }
}
