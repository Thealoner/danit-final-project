package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import com.danit.models.metamodels.Client_;
import com.danit.models.metamodels.Contract_;
import com.danit.models.metamodels.Paket_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {
  @Override
  public Specification<Contract> getFilter(ContractListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(startDateContains(request.search))
              .or(findByClientGenderSpec(request.clientGender))
      )
          .and(startDateContains(request.search))
          .and(findByClientGenderSpec(request.clientGender))
          .toPredicate(root, query, cb);

    };
  }

  private Specification<Contract> startDateContains(String startDate) {
    return stringAttributeContains("startDate", startDate);
  }

  private Specification<Contract> stringAttributeContains(String attribute, String value) {
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

//  private Specification<Contract> getContractsBasedOnGenderSpec(String gender) {
//    return (root, query, cb) -> {
//      Join<Contract, Client> contractPaketJoin = root.join(Contract_.client);
//      return cb.equal(contractPaketJoin.get(), gender);
//    };
//  }

//  public static Specification<Contract> getContractByPaketIdSpec(String paketId) {
//    return (root, query, cb) -> {
//      Long pktId = new Long(paketId);
//      Join<Contract, Paket> contractPaketJoin = root.join(Contract_.paket);
//      return cb.equal(contractPaketJoin.get(Paket_.id), pktId);
//    };
//  }

  public static Specification<Contract> findByClientGenderSpec(String gender){
    return (Specification<Contract>) (root, criteriaQuery, criteriaBuilder) -> {
      final Subquery<Long> clientQuery = criteriaQuery.subquery(Long.class);
      final Root<Client> client = clientQuery.from(Client.class);
      final Join<Client, Contract> contracts = client.join("contracts");
      clientQuery.select(contracts.get("id"));
      clientQuery.where(criteriaBuilder.equal(client.get("gender"), gender));

      return criteriaBuilder.in(root.get("id")).value(clientQuery);
    };
  }
}
