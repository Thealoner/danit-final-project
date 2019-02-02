package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
          where(dateSearch(request.search, "startDate", request.equal))
              .or(dateSearch(request.search, "endDate", request.equal))
              .or(idContains(request.search, request.equal))
              .or(creditContains(request.search, request.equal))
              .or(activeContains(request.search, request.equal))
      )
          .and(dateSearch(request.startDate, "startDate", request.equal))
          .and(dateSearch(request.endDate, "endDate", request.equal))
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

