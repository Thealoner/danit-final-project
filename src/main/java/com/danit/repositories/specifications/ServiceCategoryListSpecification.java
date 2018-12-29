package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceCategoryListRequestDto;
import com.danit.models.ServiceCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ServiceCategoryListSpecification extends BaseSpecification<ServiceCategory, ServiceCategoryListRequestDto> {
  @Override
  public Specification<ServiceCategory> getFilter(ServiceCategoryListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(titleContains(request.search))
              .or(activeContains(request.search))
      )
          .and(titleContains(request.title))
          .and(activeContains(request.active))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<ServiceCategory> titleContains(String title) {
    return attributeContains("title", title);
  }

  private Specification<ServiceCategory> activeContains(Object active) {
    return Objects.nonNull(active) ? attributeContains("active", String.valueOf(active)) :
        null;
  }


  private Specification<ServiceCategory> attributeContains(String attribute, String value) {
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
}
