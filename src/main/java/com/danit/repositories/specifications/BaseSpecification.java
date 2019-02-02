package com.danit.repositories.specifications;

import com.danit.exceptions.IllegalDateConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public abstract class BaseSpecification<T, U> {

  @Value("${global.date.pattern}")
  private String datePattern;

  @Autowired
  private SimpleDateFormat simpleDateFormat;

  @Autowired
  private Environment environment;

  private static final String WILDCARD = "%";

  public abstract Specification<T> getFilter(U request);

  public Specification<T> dateSearch(String searchDate, String columnName, Boolean equals) {
    if (Objects.nonNull(searchDate)) {
      return (root, query, cb) -> {
        simpleDateFormat.applyPattern(datePattern);
        if(searchDate.contains("/")) {
          String[] dates = searchDate.split("/");
          try {
            Date startDate = simpleDateFormat.parse(dates[0]);
            Date endDate = simpleDateFormat.parse(dates[1]);
            return cb.between(root.get(columnName),startDate, endDate);
          } catch (ParseException e) {
            throw new IllegalDateConversionException("invalid date format", e);
          }
        } else {
          try {
            Date searchDateAsDate = simpleDateFormat.parse(searchDate);
            String formatedDate = simpleDateFormat.format(searchDateAsDate);
            if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
              return equals ? cb.between(root.get(columnName), searchDateAsDate, searchDateAsDate)
                  : cb.like(cb.lower(root.get(columnName).as(String.class)), containsLowerCase(formatedDate));
            } else {
              return equals ? cb.equal(root.get(columnName), searchDateAsDate)
                  : cb.like(cb.lower(root.get(columnName).as(String.class)), containsLowerCase(formatedDate));
            }
          } catch (ParseException e) {
            throw new IllegalDateConversionException("invalid date format", e);
          }
        }
      };
    } else {
      return null;
    }
  }

  protected String containsLowerCase(String searchField) {
    return WILDCARD + searchField.toLowerCase() + WILDCARD;
  }
}
