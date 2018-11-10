package com.danit.services;

import com.danit.models.UserRoles;
import com.danit.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  UserRolesRepository userRolesRepository;

  @Override
  public void saveRole(UserRoles role) {
    userRolesRepository.save(role);
  }

  @Override
  public void deleteRoles(List<UserRoles> roles) {
    userRolesRepository.deleteAll(roles);
  }

  @Override
  public void saveAllRoles(List<UserRoles> roles) {
    userRolesRepository.saveAll(roles);
  }

  @Override
  public List<UserRoles> getAllRoles() {
    return userRolesRepository.findAll();
  }

}
