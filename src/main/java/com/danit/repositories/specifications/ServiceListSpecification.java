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

    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(idContains(request.search, request.equal))
              .or(titleContains(request.search, request.equal))
              .or(priceEquals(request.search, request.equal))
              .or(costEquals(request.search, request.equal))
              .or(unitEquals(request.search, request.equal))
              .or(unitsNumberEquals(request.search, request.equal))
              .or(activeEquals(request.search, request.equal))
              .or(getByServiceCategoryId(request.search))
      )
          .and(idContains(request.id, request.equal))
          .and(titleContains(request.title, request.equal))
          .and(priceEquals(request.price, request.equal))
          .and(costEquals(request.cost, request.equal))
          .and(unitEquals(request.unit, request.equal))
          .and(unitsNumberEquals(request.unitsNumber, request.equal))
          .and(activeEquals(request.active, request.equal))
          .and(getByServiceCategoryId(request.serviceCategoryId))
          .toPredicate(root, query, cb);
    };

  }

  private Specification<Service> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<Service> titleContains(String title, Boolean equals) {
    return equals ? attributeEquals("title", title) : attributeContains("title", title);
  }

  private Specification<Service> priceEquals(String price, Boolean equals) {
    return equals ? attributeEquals("price", price) : attributeContains("price", price);
  }

  private Specification<Service> costEquals(String cost, Boolean equals) {
    return equals ? attributeEquals("cost", cost) : attributeContains("cost", cost);
  }

  private Specification<Service> unitEquals(String unit, Boolean equals) {
    return equals ? attributeEquals("unit", unit) : attributeContains("unit", unit);
  }

  private Specification<Service> unitsNumberEquals(String unitsNumber, Boolean equals) {
    return equals ? attributeEquals("unitsNumber", unitsNumber) : attributeContains("unitsNumber", unitsNumber);
  }

  private Specification<Service> activeEquals(String active, Boolean equals) {
    return equals ? attributeEquals("active", active) : attributeContains("active", active);
  }

  public static Specification<Service> getByServiceCategoryId(String serviceCategoryId) {
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

  private Specification<Service> attributeEquals(String attribute, String value) {
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
