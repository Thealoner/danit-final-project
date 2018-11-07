package com.danit.services;

import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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
  public void deleteUserById(long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public User getUserById(long id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cant find user with id=" + id));
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void saveAllUsers(List<User> users) {
    userRepository.saveAll(users);
  }

}