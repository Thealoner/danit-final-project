package com.danit.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T, U> {

  private static final String WILDCARD = "%";

  public abstract Specification<T> getFilter(U request);

  protected String containsLowerCase(String searchField) {
    return WILDCARD + searchField.toLowerCase() + WILDCARD;
  }
}
