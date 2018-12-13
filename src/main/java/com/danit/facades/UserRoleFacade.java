package com.danit.facades;

import com.danit.dto.UserRoleDto;
import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.models.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleFacade extends AbstractDtoFacade<UserRoleDto, UserRole, UserRoleListRequestDto> {
}
