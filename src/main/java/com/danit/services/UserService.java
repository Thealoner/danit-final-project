package com.danit.services;

import com.danit.models.Role;
import com.danit.models.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    void saveUserByFields(String userName, String password, List<Role> roles);
    User getUserByUsername(String username);
    User getUserById(long id);
    List<User> getAllUsers();

}