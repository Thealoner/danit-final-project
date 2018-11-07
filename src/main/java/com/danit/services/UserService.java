package com.danit.services;

import com.danit.models.User;

import java.util.List;

public interface UserService {

  void saveUser(User user);

  void updateUser(User user);

  void deleteUserById(long id);

  User getUserByUsername(String username);

  User getUserById(long id);

  List<User> getAllUsers();

  void saveAllUsers(List<User> users);

}