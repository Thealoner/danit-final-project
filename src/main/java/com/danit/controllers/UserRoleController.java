package com.danit.controllers;


import com.danit.dto.Views;
import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.facades.UserFacade;
import com.danit.facades.UserRoleFacade;
import com.danit.models.UserRole;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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

import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_NUMBER;
import static com.danit.utils.ControllerUtils.DEFAULT_PAGE_SIZE;
import static com.danit.utils.ControllerUtils.convertDtoToMap;
import static com.danit.utils.ControllerUtils.convertPageToMap;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

  private UserRoleFacade userRoleFacade;
  private UserFacade userFacade;

  @Autowired
  public UserRoleController(UserRoleFacade userRoleFacade, UserFacade userFacade) {
    this.userRoleFacade = userRoleFacade;
    this.userFacade = userFacade;
  }

  @JsonView(Views.Extended.class)
  @PostMapping
  public ResponseEntity<Map<String, Object>> createUserRoles(@RequestBody List<UserRole> roles,
                                                             Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(userRoleFacade.saveEntities(roles)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping(path = "/ids")
  public ResponseEntity<Map<String, Object>> getAllUserRolesDtoIds(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserRoleListRequestDto userRoleListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.getAllEntities(userRoleListRequestDto, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping(path = "/short")
  public ResponseEntity<Map<String, Object>> getAllUserRolesDtoShort(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserRoleListRequestDto userRoleListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.getAllEntities(userRoleListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping
  public ResponseEntity<Map<String, Object>> getAllUserRolesDtoExtended(
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable,
      Principal principal,
      UserRoleListRequestDto userRoleListRequestDto) {
    return ResponseEntity.ok(convertPageToMap(userRoleFacade.getAllEntities(userRoleListRequestDto, pageable)));
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{id}")
  ResponseEntity<Map<String, Object>> getUserRoleByIdDtoExtended(@PathVariable(name = "id") long id, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(userRoleFacade.getEntityById(id)));
  }

  @JsonView(Views.Extended.class)
  @PutMapping
  public ResponseEntity<Map<String, Object>> updateUserRolesDto(@RequestBody List<UserRole> roles, Principal principal) {
    return ResponseEntity.ok(convertDtoToMap(userRoleFacade.updateEntities(roles)));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUserRoleById(@PathVariable(name = "id") long id, Principal principal) {
    userRoleFacade.deleteEntityById(id);
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteUserRolesDto(@RequestBody List<UserRole> roles, Principal principal) {
    userRoleFacade.deleteEntities(roles);
  }

  @JsonView(Views.Extended.class)
  @GetMapping("/{roleId}/users")
  ResponseEntity<Map<String, Object>> getAllUsersForRoleId(
      @PathVariable(name = "roleId")
          long id,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(userFacade.findUsersWithRoleId(id, pageable)));
  }

  @JsonView(Views.Short.class)
  @GetMapping("/{roleId}/users/short")
  ResponseEntity<Map<String, Object>> getAllUsersForRoleIdShort(
      @PathVariable(name = "roleId")
          long id,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(userFacade.findUsersWithRoleId(id, pageable)));
  }

  @JsonView(Views.Ids.class)
  @GetMapping("/{roleId}/users/ids")
  ResponseEntity<Map<String, Object>> getAllUsersForRoleIdIds(
      @PathVariable(name = "roleId")
          long id,
      Principal principal,
      @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
      @SortDefault.SortDefaults({
          @SortDefault(sort = "id", direction = Sort.Direction.ASC)
      }) Pageable pageable) {
    return ResponseEntity.ok(convertPageToMap(userFacade.findUsersWithRoleId(id, pageable)));
  }

}
