package com.danit.repositories.specifications;

import com.danit.dto.service.ServiceListRequestDto;
import com.danit.models.Service;
import com.danit.models.ServiceCategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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

  private Specification<Service> unitsNumberEquals(Object unitsNum) {
    return Objects.nonNull(unitsNum) ? attributeContains("unitsNumber", String.valueOf(unitsNum)) :
        null;
  }

  private Specification<Service> activeEquals(Object active) {
    return Objects.nonNull(active) ? attributeContains("active", String.valueOf(active)) :
        null;
  }

  public static Specification<Service> getByServiceCategoryId(Object serviceCategoryId) {
    if (Objects.nonNull(serviceCategoryId)) {
      return (Specification<Service>) (root, criteriaQuery, criteriaBuilder) -> {
        final Subquery<Long> servCatQuery = criteriaQuery.subquery(Long.class);
        final Root<ServiceCategory> servCat = servCatQuery.from(ServiceCategory.class);
        final Join<ServiceCategory, Service> services = servCat.join("services");
        servCatQuery.select(services.get("id"));
        servCatQuery.where(criteriaBuilder.equal(servCat.get("id"), serviceCategoryId));

        return criteriaBuilder.in(root.get("id")).value(servCatQuery);
      };
    } else {
      return null;
    }
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
