package com.danit.services;

import com.danit.models.User;

import java.util.List;

public interface UserService {

  void saveUsers(List<User> users);

  void updateUsers(List<User> users);

  void deleteUserById(long id);

  User getUserByUsername(String username);

  User getUserById(long id);

  List<User> getAllUsers();

  void saveAllUsers(List<User> users);

}