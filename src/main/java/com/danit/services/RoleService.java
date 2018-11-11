package com.danit.services;

import com.danit.models.UserRoles;

import java.util.List;

public interface RoleService {

  void deleteRoles(List<UserRoles> roles);

  List<UserRoles> saveAllRoles(List<UserRoles> roles);

  List<UserRoles> getAllRoles();

}
