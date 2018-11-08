package com.danit.services;

import com.danit.models.UserRoles;

import java.util.List;

public interface RoleService {

  void saveRole(UserRoles role);

  void saveAllRoles(List<UserRoles> roles);

  List<UserRoles> getAllRoles();

}
