package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {
  @Autowired
  private SimpleDateFormat simpleDateFormat;

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
    return attributeContains("startDate", startDate);
  }

  private Specification<Contract> endDateContains(String endDate) {
    if(Objects.nonNull(endDate)) {
      return (root, query, cb) -> {
        Path<Date> path = root.get("endDate");
        Expression<String> dateStringExpr = cb.function("TO_CHAR", String.class, path, cb.literal("dd-MM-yyyy"));
        return cb.like(dateStringExpr, ":" + endDate);
      };
    } else {
      return null;
    }
  }


  private Specification<Contract> creditContains(Object credit) {
    return Objects.nonNull(credit) ? attributeContains("credit", String.valueOf(credit)) :
        null;
  }

  private Specification<Contract> activeContains(Object active) {
    return Objects.nonNull(active) ? attributeContains("active", String.valueOf(active)) :
        null;
  }

  private Specification<Contract> clientIdContains(Object clientId) {
    return Objects.nonNull(clientId) ? attributeContains("clientId", String.valueOf(clientId)) :
        null;
  }

  private Specification<Contract> attributeContains(String attribute, String value) {
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


  public static Specification<Contract> getContractByPaketIdSpec(Object paketId) {
    if(Objects.nonNull(paketId)){
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
}
