package com.danit.facades;

import com.danit.dto.UserDto;
import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import com.danit.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserFacade extends AbstractDtoFacade<UserDto, User, UserListRequestDto> {

  private UserService userService;

  public UserFacade(UserService userService) {
    this.userService = userService;
  }

  public Page<UserDto> findUsersWithRoleId(Long id, Pageable pageable) {
    return convertToDtos(userService.findAllUsersWithRoleId(id, pageable));
  }


}
