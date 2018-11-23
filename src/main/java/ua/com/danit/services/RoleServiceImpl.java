package ua.com.danit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.danit.models.UserRoles;
import ua.com.danit.repositories.UserRolesRepository;

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
