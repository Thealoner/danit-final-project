package ua.com.danit.services;

import ua.com.danit.models.User;

import java.util.List;

public interface UserService {

  List<User> saveUsers(List<User> users);

  List<User> updateUsers(List<User> users);

  void deleteUserById(long id);

  User getUserByUsername(String username);

  User getUserById(long id);

  List<User> getAllUsers();

  void deleteUsers(List<User> users);
}