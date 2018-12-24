package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Client;
import com.danit.models.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ServiceListSpecification extends BaseSpecification<Service, ServiceListRequestDto> {
  @Override
  public Specification<Service> getFilter(ServiceListRequestDto request) {
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(titleContains(request.search))
              .or(priceEquals(request.search))
              .or(costEquals(request.search))
              .or(unitEquals(request.search))
              .or(unitsNumberEquals(request.search))
              .or(activeEquals(request.search))
              .or(getByServiceCategoryId(request.search))
      )
          .and(titleContains(request.title))
          .and(priceEquals(request.price))
          .and(costEquals(request.cost))
          .and(unitEquals(request.unit))
          .and(activeEquals(request.unitsNumber))
          .and(getByServiceCategoryId(request.serviceCategoryId))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Service> titleContains(String title) {
    return attributeContains("title", title);
  }

  private Specification<Service> priceEquals(Object price) {
    return Objects.nonNull(price) ? attributeContains("price", String.valueOf(price)) :
        null;
  }

  private Specification<Service> costEquals(Object cost) {
    return Objects.nonNull(cost) ? attributeContains("cost", String.valueOf(cost)) :
        null;
  }

  private Specification<Service> unitEquals(Object unit) {
    return Objects.nonNull(unit) ? attributeContains("unit", String.valueOf(unit)) :
        null;
  }

  private Specification<Service> activeEquals(Object active) {
    return Objects.nonNull(active) ? attributeContains("active", String.valueOf(active)) :
        null;
  }

  private Specification<Service> attributeContains(String attribute, String value) {
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
