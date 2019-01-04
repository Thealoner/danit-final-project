package com.danit.repositories.specifications;

import com.danit.dto.service.CardListRequestDto;
import com.danit.models.Card;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class CardListSpecification extends BaseSpecification<Card, CardListRequestDto> {

  @Override
  public Specification<Card> getFilter(CardListRequestDto request) {
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(cardCodeContains(request.search, request.equal))
              .or(cardIdContains(request.search, request.equal))
              .or(cardIsActive(request.search, request.equal))
      )
          .and(cardCodeContains(request.code, request.equal))
          .and(cardIdContains(request.id, request.equal))
          .and(cardIsActive(request.active, request.equal))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Card> cardIdContains(String id, Boolean equal) {
    return equal ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<Card> cardCodeContains(String code, Boolean equal) {
    return equal ? attributeEquals("code", code) : attributeContains("code", code);
  }

  private Specification<Card> cardIsActive(String active, Boolean equal) {
    return equal ? attributeEquals("active", active) : attributeContains("active", active);
  }

  private Specification<Card> attributeContains(String attribute, String value) {
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

  private Specification<Card> attributeEquals(String attribute, String value) {
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
