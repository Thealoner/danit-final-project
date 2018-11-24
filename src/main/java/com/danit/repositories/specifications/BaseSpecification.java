package com.danit.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

public abstract class BaseSpecification<T, U> {

  private final static String wildcard = "%";

  public abstract Specification<T> getFilter(U request);

  protected String containsLowerCase(String searchField) {
    return wildcard + searchField.toLowerCase() + wildcard;
  }
}
