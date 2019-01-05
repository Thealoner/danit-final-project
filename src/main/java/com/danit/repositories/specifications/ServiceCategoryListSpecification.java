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
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(titleContains(request.search, request.equal))
              .or(activeContains(request.search, request.equal))
              .or(idContains(request.search, request.equal))
      )
          .and(idContains(request.id, request.equal))
          .and(titleContains(request.title, request.equal))
          .and(activeContains(request.active, request.equal))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<ServiceCategory> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<ServiceCategory> titleContains(String title, Boolean equals) {
    return equals ? attributeEquals("title", title) : attributeContains("title", title);
  }

  private Specification<ServiceCategory> activeContains(String active, Boolean equals) {
    return equals ? attributeEquals("active", active) : attributeContains("active", active);

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

  private Specification<ServiceCategory> attributeEquals(String attribute, String value) {
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
