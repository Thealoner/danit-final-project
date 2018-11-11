package com.danit.services;

import com.danit.models.UserRoles;
import com.danit.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  private UserRolesRepository userRolesRepository;

  @Autowired
  public RoleServiceImpl(UserRolesRepository userRolesRepository) {
    this.userRolesRepository = userRolesRepository;
  }

  @Override
  public void deleteRoles(List<UserRoles> roles) {
    userRolesRepository.deleteAll(roles);
  }

  @Override
  public List<UserRoles> saveAllRoles(List<UserRoles> roles) {
    return userRolesRepository.saveAll(roles);
  }

  @Override
  public List<UserRoles> getAllRoles() {
    return userRolesRepository.findAll();
  }

}
