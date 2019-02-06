package com.danit.facades;

import com.danit.dto.UserRoleDto;
import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.models.UserRole;
import com.danit.services.UserRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserRoleFacade extends AbstractDtoFacade<UserRoleDto, UserRole, UserRoleListRequestDto> {

  private UserRoleService userRoleService;

  public UserRoleFacade(UserRoleService userRoleService) {
    this.userRoleService = userRoleService;
  }

  public Page<UserRoleDto> findAllRolesDtoForUserId(Long id, Pageable pageable) {
    return convertToDtos(userRoleService.findAllRolesForUserId(id, pageable));
  }
}
