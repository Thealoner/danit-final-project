package com.danit.services;

import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.models.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService extends AbstractBaseEntityService<UserRole, UserRoleListRequestDto> {

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
}
