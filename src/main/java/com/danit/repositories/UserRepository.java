package com.danit.repositories;

import com.danit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends EntityRepository<User> {
  User findByUsername(String name);
}
