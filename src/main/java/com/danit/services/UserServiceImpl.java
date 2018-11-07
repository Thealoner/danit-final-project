package com.danit.services;

import com.danit.exceptions.UserNameIsAlreadyExistInDb;
import com.danit.models.User;
import com.danit.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    if (Objects.nonNull(userRepository.findByUsername(user.getUsername()))) {
      throw new UserNameIsAlreadyExistInDb("User with name=" + user.getUsername() + " already exist in DB, but it should be unique");
    }
    user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public void updateUser(User user) {
    userRepository.findById(user.getId())
        .orElseThrow(() -> new EntityNotFoundException("User with id=" + user.getId() + " is not exist"));
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
    return userRepository.findById(id).orElseThrow(() ->
        new EntityNotFoundException("Cant find user with id=" + id));
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void saveAllUsers(List<User> users) {
    Set<String> userNames = userRepository.findAllUserNames();
    users.forEach(user -> {
      if(userNames.contains(user.getUsername())) {
        throw new UserNameIsAlreadyExistInDb("User with name=" + user.getUsername() +
            " already exist in DB, but it should be unique");
      }
    });

    users.forEach(user -> user.setPassword(bcryptPasswordEncoder.encode(user.getPassword())));
    userRepository.saveAll(users);
  }
}