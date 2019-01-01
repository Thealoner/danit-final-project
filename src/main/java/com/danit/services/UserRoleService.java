package com.danit.services;

import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.models.User;
import com.danit.models.UserRole;
import com.danit.repositories.UserRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService extends AbstractBaseEntityService<UserRole, UserRoleListRequestDto> {

  private UserService userService;
  private UserRoleRepository userRoleRepository;

  public UserRoleService(UserService userService, UserRoleRepository userRoleRepository) {
    this.userService = userService;
    this.userRoleRepository = userRoleRepository;
  }


  @Transactional
  @Override
  public void deleteEntityById(long id) {
    UserRole userRole = getEntityById(id);
    userRole.getUsers().forEach(user -> user.getRoles().remove(userRole));
    baseEntityRepository.deleteById(id);
  }

  @Override
  public void deleteEntities(List<UserRole> entityList) {
    List<UserRole> userRoles = reloadEntities(entityList);
    userRoles.forEach(userRole -> userRole.getUsers().forEach(user -> user.getRoles().remove(userRole)));
    baseEntityRepository.deleteAll(userRoles);
  }

  @Transactional
  public void assignRoleToUser(Long userId, Long roleId) {
    User user = userService.getEntityById(userId);
    UserRole userRole = getEntityById(roleId);

    Boolean containsRole = user.getRoles()
        .stream().filter(role -> role.getId() == roleId)
        .map(UserRole::getId).findFirst().isPresent();

    if (!containsRole) {
      user.getRoles().add(userRole);
      userRole.getUsers().add(user);
    }
  }

  @Transactional
  public void assignRolesToUser(Long userId, List<UserRole> roles) {
    User user = userService.getEntityById(userId);


    List<Long> usersRolesIds = user.getRoles()
        .stream().map(UserRole::getId).collect(Collectors.toList());

    List<Long> targetIds =
        roles.stream()
            .map(UserRole::getId)
            .filter(inputId -> !usersRolesIds.contains(inputId))
            .collect(Collectors.toList());


    List<UserRole> targetUserRoles = userRoleRepository.findAllEntitiesByIds(targetIds);

    targetUserRoles.forEach(userRole -> user.getRoles().add(userRole));
    targetUserRoles.forEach(userRole -> userRole.getUsers().add(user));
  }

  @Transactional
  public void deleteRoleFromUser(Long userId, Long roleId) {
    User user = userService.getEntityById(userId);
    UserRole role = getEntityById(roleId);
    role.getUsers().remove(user);
    user.getRoles().remove(role);
  }

  @Transactional
  public void deleteRolesFromUser(Long userId, List<UserRole> roles) {
    User user = userService.getEntityById(userId);
    List<UserRole> userRoles = reloadEntities(roles);

    userRoles.forEach(role -> user.getRoles().remove(role));
    userRoles.forEach(role -> role.getUsers().remove(user));
  }

  public Page<UserRole> findAllRolesForUserId(long id, Pageable pageable) {
    return userRoleRepository.findAllRolesForUserId(id, pageable);
  }
}
