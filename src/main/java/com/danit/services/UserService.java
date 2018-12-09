package com.danit.services;

import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractEntityService<User, UserListRequestDto> {
}