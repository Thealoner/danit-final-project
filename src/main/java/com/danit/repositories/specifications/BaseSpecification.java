package com.danit.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;

public abstract class BaseSpecification<T, U> {

  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

  private static final String WILDCARD = "%";

  public abstract Specification<T> getFilter(U request);

  protected String containsLowerCase(String searchField) {
    return WILDCARD + searchField.toLowerCase() + WILDCARD;
  }
}
