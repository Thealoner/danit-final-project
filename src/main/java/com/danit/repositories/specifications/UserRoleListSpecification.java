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
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(userRoleName(request.search, request.equal))
              .or(idContains(request.search, request.equal))
      )
          .and(idContains(request.id, request.equal))
          .and(userRoleName(request.role, request.equal))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<UserRole> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<UserRole> userRoleName(String role, Boolean equals) {
    return equals ? attributeEquals("role", role) : attributeContains("role", role);
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

  private Specification<UserRole> attributeEquals(String attribute, String value) {
    return (root, query, cb) -> {
      if (value == null) {
        return null;
      }
      return cb.equal(
          cb.lower(root.get(attribute).as(String.class)),
          value.toLowerCase()
      );
    };
  }
}
