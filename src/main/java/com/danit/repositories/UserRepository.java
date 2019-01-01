package com.danit.repositories;

import com.danit.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseEntityRepository<User> {
  User findByUsername(String name);

  @Query("select u from User u left join u.roles r where r.id = :roleId")
  Page<User> findAllUsersWithRoleId(@Param(value = "roleId") Long roleId,
                                  Pageable pageable);
}
