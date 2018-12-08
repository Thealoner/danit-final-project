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
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(cardCodeContains(request.search))
              .or(cardIdContains(request.search))
              .or(cardIsActive(request.search))
      )
          .and(cardCodeContains(request.code))
          .and(cardIdContains(request.id))
          .and(cardIsActive(request.active))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Card> cardIdContains(Object id) {
    return Objects.nonNull(id) ? attributeContains("id",
        String.valueOf(id)) :
        null;
  }

  private Specification<Card> cardCodeContains(String code) {
    return attributeContains("code", code);
  }

  private Specification<Card> cardIsActive(Object active) {
    return Objects.nonNull(active) ? attributeContains("active",
        String.valueOf(active)) :
        null;
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
}
