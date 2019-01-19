package com.danit.repositories.specifications;

import com.danit.dto.service.ClientListRequestDto;
import com.danit.models.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.Date;
import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ClientListSpecification extends BaseSpecification<Client, ClientListRequestDto> {

  @Override
  public Specification<Client> getFilter(ClientListRequestDto request) {
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(firstNameContains(request.search, request.equal))
              .or(idContains(request.search, request.equal))
              .or(lastNameContains(request.search, request.equal))
              .or(emailContains(request.search, request.equal))
              .or(phoneNumberContains(request.search, request.equal))
              .or(genderContains(request.search, request.equal))
//              .or(birthDateContains(request.search, request.equal))
              .or(birthDateBetween(request.search, request.equal))
              .or(activeContains(request.search, request.equal))
      )
          .and(idContains(request.id, request.equal))
          .and(firstNameContains(request.firstName, request.equal))
          .and(lastNameContains(request.lastName, request.equal))
          .and(genderContains(request.gender, request.equal))
          .and(emailContains(request.email, request.equal))
          .and(phoneNumberContains(request.phoneNumber, request.equal))
//          .and(birthDateContains(request.birthDate, request.equal))
          .and(birthDateBetween(request.birthDate, request.equal))
          .and(activeContains(request.active, request.equal))
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

//  private Specification<Client> birthDateContains(String birthDate, Boolean equals) {
//    if (Objects.nonNull(birthDate)) {
//      return (root, query, cb) -> {
//        Path<Tuple> tuple = root.<Tuple>get("birthDate");
//        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
//          Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
//              root.get("birthDate"), cb.literal("dd-MM-yyyy"));
//          return equals ? cb.like(cb.lower(dateStringExpr), birthDate.toLowerCase())
//              : cb.like(cb.lower(dateStringExpr), containsLowerCase(birthDate));
//        } else {
//          return null;
//        }
//      };
//    } else {
//      return null;
//    }
//  }

    private Specification<Client> birthDateBetween(String birthDate, Boolean equals) {
    if (Objects.nonNull(birthDate)) {
      return (root, query, cb) -> {
        Path<Tuple> tuple = root.<Tuple>get("birthDate");
        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
          Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
              root.get("birthDate"), cb.literal("dd-MM-yyyy"));
          if(birthDate.contains("|")) {
            String[] dates = birthDate.split("\\|");
            String startDate = dates[0];
            System.out.println(startDate);
            String endDate = dates[1];
            System.out.println(endDate);
//            return cb.between(dateStringExpr, startDate, endDate);
            return cb.and(cb.greaterThanOrEqualTo(dateStringExpr, startDate), cb.lessThanOrEqualTo(dateStringExpr, endDate));
          } else {
            return equals ? cb.like(cb.lower(dateStringExpr), birthDate.toLowerCase())
                : cb.like(cb.lower(dateStringExpr), containsLowerCase(birthDate));
          }
        } else {
          return null;
        }
      };
    } else {
      return null;
    }
  }

  private Specification<Client> activeContains(String active, Boolean equals) {
    return equals ? attributeEquals("active", active) : attributeContains("active", active);
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
