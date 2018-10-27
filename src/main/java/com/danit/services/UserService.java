package com.danit.services;

import com.danit.models.Role;
import com.danit.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  void saveUser(User user);

  void saveUserByFields(String userName, String password, List<Role> roles);

  User getUserByUsername(String username);

  Optional<User> getUserById(long id);

  List<User> getAllUsers();

}