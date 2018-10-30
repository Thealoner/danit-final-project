package com.danit.services;

import com.danit.models.User;
import com.danit.models.UserRoles;
import com.danit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private BCryptPasswordEncoder bcryptPasswordEncoder;

  UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bcryptPasswordEncoder = bcryptPasswordEncoder;
  }

  @Override
  public void saveUser(User user) {
    user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public void saveUserByFields(String userName, String password, List<UserRoles> roles) {
    User user = new User(userName, bcryptPasswordEncoder.encode(password), roles);
    userRepository.save(user);
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public Optional<User> getUserById(long id) {
    return userRepository.findById(id);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

}