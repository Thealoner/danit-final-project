package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {

  @Autowired
  Environment environment;

  public static Specification<Contract> getContractByPaketIdSpec(String paketId) {
    if (Objects.nonNull(paketId)) {
      return (Specification<Contract>) (root, criteriaQuery, criteriaBuilder) -> {
        final Subquery<Long> paketQuery = criteriaQuery.subquery(Long.class);
        final Root<Paket> paket = paketQuery.from(Paket.class);
        final Join<Paket, Contract> contracts = paket.join("contracts");
        paketQuery.select(contracts.get("id"));
        paketQuery.where(criteriaBuilder.equal(paket.get("id"), paketId));

        return criteriaBuilder.in(root.get("id")).value(paketQuery);
      };
    } else {
      return null;
    }
  }

  public static Specification<Contract> findByClientGenderSpec(String gender) {
    if (Objects.nonNull(gender)) {
      return (Specification<Contract>) (root, criteriaQuery, criteriaBuilder) -> {
        final Subquery<Long> clientQuery = criteriaQuery.subquery(Long.class);
        final Root<Client> client = clientQuery.from(Client.class);
        final Join<Client, Contract> contracts = client.join("contracts");
        clientQuery.select(contracts.get("id"));
        clientQuery.where(criteriaBuilder.equal(client.get("gender"), gender));

        return criteriaBuilder.in(root.get("id")).value(clientQuery);
      };
    } else {
      return null;
    }
  }

  @Override
  public Specification<Contract> getFilter(ContractListRequestDto request) {

    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(startDateContains(request.search, request.equal))
              .or(endDateContains(request.search, request.equal))
              .or(idContains(request.search, request.equal))
              .or(creditContains(request.search, request.equal))
              .or(activeContains(request.search, request.equal))
      )
          .and(startDateContains(request.startDate, request.equal))
          .and(endDateContains(request.endDate, request.equal))
          .and(idContains(request.id, request.equal))
          .and(creditContains(request.credit, request.equal))
          .and(activeContains(request.getActive(), request.equal))
          .and(clientIdContains(request.clientId))
          .and(getContractByPaketIdSpec(request.paketId))
          .and(findByClientGenderSpec(request.clientGender))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Contract> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<Contract> startDateContains(String startDate, Boolean equals) {
    if (Objects.nonNull(startDate)) {
      return (root, query, cb) -> {
        Path<Tuple> tuple = root.<Tuple>get("startDate");
        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
          if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            Expression<String> dateStringExpr = cb.function("DATE_FORMAT", String.class,
                root.get("startDate"), cb.literal("%d-%m-%Y"));
            return equals ? cb.like(cb.lower(dateStringExpr), startDate.toLowerCase())
                : cb.like(cb.lower(dateStringExpr), containsLowerCase(startDate));
          } else {
            Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
                root.get("startDate"), cb.literal("dd-MM-yyyy"));
            return equals ? cb.like(cb.lower(dateStringExpr), startDate.toLowerCase())
                : cb.like(cb.lower(dateStringExpr), containsLowerCase(startDate));
          }
        } else {
          return null;
        }
      };
    } else {
      return null;
    }
  }

  private Specification<Contract> endDateContains(String endDate, Boolean equals) {
    if (Objects.nonNull(endDate)) {
      return (root, query, cb) -> {
        Path<Tuple> tuple = root.<Tuple>get("endDate");
        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
          if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            Expression<String> dateStringExpr = cb.function("DATE_FORMAT", String.class,
                root.get("endDate"), cb.literal("%d-%m-%Y"));
            return equals ? cb.like(cb.lower(dateStringExpr), endDate.toLowerCase())
                : cb.like(cb.lower(dateStringExpr), containsLowerCase(endDate));
          } else {
            Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
                root.get("endDate"), cb.literal("dd-MM-yyyy"));
            return equals ? cb.like(cb.lower(dateStringExpr), endDate.toLowerCase())
                : cb.like(cb.lower(dateStringExpr), containsLowerCase(endDate));
          }
        } else {
          return null;
        }
      };
    } else {
      return null;
    }
  }

  private Specification<Contract> creditContains(String credit, Boolean equals) {
    return equals ? attributeEquals("credit", credit) : attributeContains("credit", credit);
  }

  private Specification<Contract> activeContains(String active, Boolean equals) {
    return equals ? attributeEquals("active", active) : attributeContains("active", active);
  }

  private Specification<Contract> attributeContains(String attribute, String value) {
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

  private Specification<Contract> attributeEquals(String attribute, String value) {
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

  private Specification<Contract> clientIdContains(String clientId) {
    if (Objects.nonNull(clientId)) {

      return (Specification<Contract>) (root, criteriaQuery, criteriaBuilder) -> {
        final Subquery<Long> clientQuery = criteriaQuery.subquery(Long.class);
        final Root<Client> client = clientQuery.from(Client.class);
        final Join<Client, Contract> contracts = client.join("contracts");
        clientQuery.select(contracts.get("id"));
        clientQuery.where(criteriaBuilder.equal(client.get("id"), clientId));

        return criteriaBuilder.in(root.get("id")).value(clientQuery);
      };
    } else {
      return null;
    }
  }

}

