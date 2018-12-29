package com.danit.services;

import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractBaseEntityService<User, UserListRequestDto> {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<User> findAllUsersWithRoleId(long id, Pageable pageable) {
    return userRepository.findAllUsersWithRoleId(id, pageable);
  }
}