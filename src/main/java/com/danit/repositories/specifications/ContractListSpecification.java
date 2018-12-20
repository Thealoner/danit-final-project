package com.danit.repositories.specifications;

import com.danit.dto.service.ContractListRequestDto;
import com.danit.models.Contract;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ContractListSpecification extends BaseSpecification<Contract, ContractListRequestDto> {
  @Override
  public Specification<Contract> getFilter(ContractListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          startDateContains(request.search)
      )
          .and(startDateContains(request.startDate))
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
}
