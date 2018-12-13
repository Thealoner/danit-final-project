package com.danit.repositories.specifications;

import com.danit.dto.service.UserRoleListRequestDto;
import com.danit.models.UserRole;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserRoleListSpecification extends BaseSpecification<UserRole, UserRoleListRequestDto> {
  @Override
  public Specification<UserRole> getFilter(UserRoleListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(userRoleName(request.search))
      )
          .and(userRoleName(request.role))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<UserRole> userRoleName(Object role) {
    return Objects.nonNull(role) ? attributeContains("role", String.valueOf(role)) :
        null;
  }

  private Specification<UserRole> attributeContains(String attribute, String value) {
    return (root, query, cb) -> {
      if (value == null) {
        return null;
      }

      return cb.like(
          cb.lower(root.get(attribute).as(String.class)),
          containsLowerCase(value)
      );
    };
  }
}
