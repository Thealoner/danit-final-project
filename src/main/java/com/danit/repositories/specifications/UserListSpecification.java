package com.danit.repositories.specifications;

import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserListSpecification extends BaseSpecification<User, UserListRequestDto> {
  @Override
  public Specification<User> getFilter(UserListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(userNameContains(request.search))
      )
          .and(userNameContains(request.username))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<User> userNameContains(String userName) {
    return attributeContains("username", userName);
  }

  private Specification<User> attributeContains(String attribute, String value) {
    return (root, query, cb) -> {
      if (value == null) {
        return null;
      }

      return cb.like(
          cb.lower(root.get(attribute)),
          containsLowerCase(value)
      );
    };
  }
}
