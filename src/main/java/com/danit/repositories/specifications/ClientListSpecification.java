package com.danit.repositories.specifications;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ClientListSpecification extends BaseSpecification<Client, ClientListRequestDto> {

  @Override
  public Specification<Client> getFilter(ClientListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(firstNameContains(request.search))
              .or(lastNameContains(request.search))
              .or(emailContains(request.search))
              .or(phoneNumberContains(request.search))
              .or(genderContains(request.search))
      )
          .and(firstNameContains(request.firstName))
          .and(lastNameContains(request.lastName))
          .and(genderContains(request.gender))
          .and(emailContains(request.email))
          .and(phoneNumberContains(request.phoneNumber))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Client> firstNameContains(String firstName) {
    return attributeContains("firstName", firstName);
  }

  private Specification<Client> lastNameContains(String lastName) {
    return attributeContains("lastName", lastName);
  }

  private Specification<Client> genderContains(String gender) {
    return attributeContains("gender", gender);
  }

  private Specification<Client> emailContains(String email) {
    return attributeContains("email", email);
  }

  private Specification<Client> phoneNumberContains(String phoneNumber) {
    return attributeContains("phoneNumber", phoneNumber);
  }

  private Specification<Client> attributeContains(String attribute, String value) {
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
