package com.danit.repositories;

import com.danit.models.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends BaseEntityRepository<UserRole> {

  @Query("select ur from UserRole ur left join ur.users u where u.id = :userId")
  Page<UserRole> findAllRolesForUserId(@Param(value = "userId") Long userId,
                                       Pageable pageable);
}
