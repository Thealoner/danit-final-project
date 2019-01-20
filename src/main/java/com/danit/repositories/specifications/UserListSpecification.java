package com.danit.repositories.specifications;

import com.danit.dto.service.UserListRequestDto;
import com.danit.models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class UserListSpecification extends BaseSpecification<User, UserListRequestDto> {
  @Override
  public Specification<User> getFilter(UserListRequestDto request) {
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(userNameContains(request.search, request.equal))
              .or(idContains(request.search, request.equal))
              .or(emailContains(request.search, request.equal))
      )
          .and(userNameContains(request.username, request.equal))
          .and(idContains(request.id, request.equal))
          .and(emailContains(request.email, request.equal))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<User> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<User> userNameContains(String username, Boolean equals) {
    return equals ? attributeEquals("username", username) : attributeContains("username", username);
  }

  private Specification<User> emailContains(String email, Boolean equals) {
    return equals ? attributeEquals("email", email) : attributeContains("email", email);
  }

  private Specification<User> attributeContains(String attribute, String value) {
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

  private Specification<User> attributeEquals(String attribute, String value) {
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
