package com.danit.facades;

import com.danit.dto.UserDto;
import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade extends AbstractDtoFacade<UserDto, User, UserListRequestDto> {
}
