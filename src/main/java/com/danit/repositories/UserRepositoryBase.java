package com.danit.repositories;

import com.danit.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryBase extends BaseEntityRepository<User> {
  User findByUsername(String name);
}
