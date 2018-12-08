package com.danit.repositories;

import com.danit.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends EntityRepository<User> {
  User findByUsername(String name);

  @Query("select u.id from User u")
  Set<Long> getAllUsersId();

  @Query("select u.username from User u")
  Set<String> findAllUserNames();
}
