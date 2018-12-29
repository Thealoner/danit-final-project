package com.danit.repositories.specifications;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ClientListSpecification extends BaseSpecification<Client, ClientListRequestDto> {

  @Override
  public Specification<Client> getFilter(ClientListRequestDto request) {
    request.equals = Objects.isNull(request.equals) ? false : request.equals;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(firstNameContains(request.search, request.equals))
              .or(idContains(request.search, request.equals))
              .or(lastNameContains(request.search, request.equals))
              .or(emailContains(request.search, request.equals))
              .or(phoneNumberContains(request.search, request.equals))
              .or(genderContains(request.search, request.equals))
      )
          .and(idContains(request.id, request.equals))
          .and(firstNameContains(request.firstName, request.equals))
          .and(lastNameContains(request.lastName, request.equals))
          .and(genderContains(request.gender, request.equals))
          .and(emailContains(request.email, request.equals))
          .and(phoneNumberContains(request.phoneNumber, request.equals))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Client> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<Client> firstNameContains(String firstName, Boolean equals) {
    return equals ? attributeEquals("firstName", firstName) : attributeContains("firstName", firstName);
  }

  private Specification<Client> lastNameContains(String lastName, Boolean equals) {
    return equals ? attributeEquals("lastName", lastName) : attributeContains("lastName", lastName);
  }

  private Specification<Client> genderContains(String gender, Boolean equals) {
    return equals ? attributeEquals("gender", gender) : attributeContains("gender", gender);
  }

  private Specification<Client> emailContains(String email, Boolean equals) {
    return equals ? attributeEquals("email", email) : attributeContains("email", email);
  }

  private Specification<Client> phoneNumberContains(String phoneNumber, Boolean equals) {
    return equals ? attributeEquals("phoneNumber", phoneNumber) : attributeContains("phoneNumber", phoneNumber);
  }

  private Specification<Client> attributeContains(String attribute, String value) {
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

  private Specification<Client> attributeEquals(String attribute, String value) {
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
