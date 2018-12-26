package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {

  @Override
  public Specification<Contract> getFilter(ContractListRequestDto request) {

    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(startDateContains(request.search))
              .or(endDateContains(request.search))
              .or(creditContains(request.search))
              .or(activeContains(request.search))
              .or(clientIdContains(request.search))
              .or(getContractByPaketIdSpec(request.search))
              .or(findByClientGenderSpec(request.search))
      )
          .and(startDateContains(request.startDate))
          .and(endDateContains(request.endDate))
          .and(creditContains(request.credit))
          .and(activeContains(request.getActive()))
          .and(clientIdContains(request.clientId))
          .and(getContractByPaketIdSpec(request.paketId))
          .and(findByClientGenderSpec(request.clientGender))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Contract> startDateContains(String startDate) {
    if (Objects.nonNull(startDate)) {
      return (root, query, cb) -> {
        Path<Tuple> tuple = root.<Tuple>get("startDate");
        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
          Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
              root.get("startDate"), cb.literal("dd-MM-yyyy"));
          return cb.like(cb.lower(dateStringExpr), containsLowerCase(startDate));
        } else {
          return null;
        }
      };
    } else {
      return null;
    }
  }

  private Specification<Contract> endDateContains(String endDate) {
    if (Objects.nonNull(endDate)) {
      return (root, query, cb) -> {
        Path<Tuple> tuple = root.<Tuple>get("endDate");
        if (tuple.getJavaType().isAssignableFrom(Date.class)) {
          Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class,
              root.get("endDate"), cb.literal("dd-MM-yyyy"));
          return cb.like(cb.lower(dateStringExpr), containsLowerCase(endDate));
        } else {
          return null;
        }
      };
    } else {
      return null;
    }
  }

  private Specification<Contract> creditContains(String  credit) {
    return attributeContains("credit", credit);
  }

  private Specification<Contract> activeContains(String active) {
    return attributeContains("active", active);
  }

  private Specification<Contract> attributeContains(String attribute, String value) {
    if (Objects.nonNull(value)) {
      return (root, query, cb) -> {
        return cb.like(cb.lower(root.get(attribute).as(String.class)), containsLowerCase(value));
      };
    } else {
      return null;
    }
  }

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

