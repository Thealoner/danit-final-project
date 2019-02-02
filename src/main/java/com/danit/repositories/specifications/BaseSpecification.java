package com.danit.repositories.specifications;

import com.danit.exceptions.IllegalDateConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
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
        if (searchDate.contains("/")) {
          simpleDateFormat.applyPattern(datePattern);
          String[] dates = searchDate.split("/");
          try {
            Date startDate = simpleDateFormat.parse(dates[0]);
            Date endDate = simpleDateFormat.parse(dates[1]);
            return cb.between(root.get(columnName),startDate, endDate);
          } catch (ParseException e) {
            throw new IllegalDateConversionException("invalid date format 1", e);
          }
        } else {
          Expression<String> dateStringExpr;
          if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {
            dateStringExpr = cb.function("DATE_FORMAT", String.class,
                root.get(columnName), cb.literal("%Y-%m-%d"));
          } else {
            dateStringExpr = cb.function("TO_CHAR", String.class,
                root.get(columnName), cb.literal("yyyy-MM-dd"));
          }
          return equals ? cb.equal(dateStringExpr, searchDate)
              : cb.like(cb.lower(dateStringExpr), containsLowerCase(searchDate));
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
