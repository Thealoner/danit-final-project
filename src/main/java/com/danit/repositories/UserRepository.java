package com.danit.repositories;

import com.danit.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {

  User findByUsername(String name);

  Page<User> findAllByRoles_Id(Long roleId, Pageable pageable);

}
