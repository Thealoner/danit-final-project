package com.danit.repositories;

import com.danit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String name);

  @Query("select u.id from User u")
  Set<Long> getAllUsersId();

  @Query("select u.username from User u")
  Set<String> findAllUserNames();
}
