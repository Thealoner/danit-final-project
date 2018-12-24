package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Client;
import com.danit.models.Contract;
import com.danit.models.Paket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {
  @Override
  public Specification<Contract> getFilter(ContractListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return
          where(startDateContains(request.search))
              .or(findByClientGenderSpec(request.clientGender))
              .or(getContractByPaketIdSpec(request.paketId))

//          .and(startDateContains(request.search))
//          .and(findByClientGenderSpec(request.clientGender))
//          .and(getContractByPaketIdSpec(request.paketId))
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


  public static Specification<Contract> getContractByPaketIdSpec(String paketId) {
    Long pktId = new Long(paketId);
    return (Specification<Contract>) (root, criteriaQuery, criteriaBuilder) -> {
      final Subquery<Long> paketQuery = criteriaQuery.subquery(Long.class);
      final Root<Paket> paket = paketQuery.from(Paket.class);
      final Join<Paket, Contract> contracts = paket.join("contracts");
      paketQuery.select(contracts.get("id"));
      paketQuery.where(criteriaBuilder.equal(paket.get("id"), pktId));

      return criteriaBuilder.in(root.get("id")).value(paketQuery);
    };
  }

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
