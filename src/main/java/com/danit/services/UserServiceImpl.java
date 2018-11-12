package com.danit.services;

import com.danit.exceptions.EntityNameIsAlreadyExistInDb;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private BCryptPasswordEncoder bcryptPasswordEncoder;

  UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bcryptPasswordEncoder = bcryptPasswordEncoder;
  }

  @Override
  public void updateUsers(List<User> users) {
    Set<Long> usersId = userRepository.getAllUsersId();
    users.forEach(user -> {
      if (!usersId.contains(user.getId())) {
        throw new EntityNotFoundException("User with id=" + user.getId() + " is not exist");
      }
    });
    users.forEach(user -> user.setPassword(bcryptPasswordEncoder.encode(user.getPassword())));
    userRepository.saveAll(users);
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
    return userRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find user with id=" + id));
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void deleteUsers(List<User> users) {
    Set<Long> usersId = userRepository.getAllUsersId();
    users.forEach(user -> {
      if (!usersId.contains(user.getId())) {
        throw new EntityNotFoundException("User with id=" + user.getId() + " is not exist");
      }
    });
    userRepository.deleteInBatch(users);
  }

  @Override
  public List<User> saveUsers(List<User> users) {
    Set<String> userNames = userRepository.findAllUserNames();
    users.forEach(user -> {
      if (userNames.contains(user.getUsername())) {
        throw new EntityNameIsAlreadyExistInDb("User with name=" + user.getUsername() +
            " already exist in DB, but it should be unique");
      }
    });
    users.forEach(user -> user.setPassword(bcryptPasswordEncoder.encode(user.getPassword())));
    return userRepository.saveAll(users);
  }
}