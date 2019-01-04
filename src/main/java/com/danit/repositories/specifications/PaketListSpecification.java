package com.danit.repositories.specifications;

import com.danit.dto.service.PaketListRequestDto;
import com.danit.models.Paket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class PaketListSpecification extends BaseSpecification<Paket, PaketListRequestDto> {

  @Override
  public Specification<Paket> getFilter(PaketListRequestDto request) {
    request.equal = Objects.isNull(request.equal) ? false : request.equal;
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(titleContains(request.search, request.equal))
              .or(idContains(request.search, request.equal))
              .or(termContains(request.search, request.equal))
              .or(priceContains(request.search, request.equal))
              .or(freezeTimesContains(request.search, request.equal))
              .or(freezeDaysContains(request.search, request.equal))
              .or(freezeMinTermContains(request.search, request.equal))
              .or(accessWithoutCardTimesLimitContains(request.search, request.equal))
              .or(autoActivateAfterDaysContains(request.search, request.equal))
              .or(guestVisitsContains(request.search, request.equal))
              .or(openDateAllowedContains(request.search, request.equal))
              .or(usersMinContains(request.search, request.equal))
              .or(limitVisitTimeContains(request.search, request.equal))
              .or(visitTimeContains(request.search, request.equal))
              .or(limitAdditionalServicesContains(request.search, request.equal))
              .or(limitUsageByPaymentPercentageContains(request.search, request.equal))
              .or(isActiveContains(request.search, request.equal))
              .or(purchasableContains(request.search, request.equal))
      )
          .and(idContains(request.id, request.equal))
          .and(titleContains(request.title, request.equal))
          .and(termContains(request.term, request.equal))
          .and(priceContains(request.price, request.equal))
          .and(freezeTimesContains(request.freezeTimes, request.equal))
          .and(freezeDaysContains(request.freezeDays, request.equal))
          .and(freezeMinTermContains(request.freezeMinTerm, request.equal))
          .and(accessWithoutCardTimesLimitContains(request.accessWithoutCardTimesLimit, request.equal))
          .and(autoActivateAfterDaysContains(request.autoActivateAfterDays, request.equal))
          .and(guestVisitsContains(request.guestVisits, request.equal))
          .and(openDateAllowedContains(request.openDateAllowed, request.equal))
          .and(usersMinContains(request.usersMin, request.equal))
          .and(limitVisitTimeContains(request.limitVisitTime, request.equal))
          .and(visitTimeContains(request.visitTime, request.equal))
          .and(limitAdditionalServicesContains(request.limitAdditionalServices, request.equal))
          .and(limitUsageByPaymentPercentageContains(request.limitUsageByPaymentPercentage, request.equal))
          .and(isActiveContains(request.active, request.equal))
          .and(purchasableContains(request.purchasable, request.equal))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Paket> idContains(String id, Boolean equals) {
    return equals ? attributeEquals("id", id) : attributeContains("id", id);
  }

  private Specification<Paket> titleContains(String title, Boolean equals) {
    return equals ? attributeEquals("title", title) : attributeContains("title", title);
  }

  private Specification<Paket> termContains(String term, Boolean equals) {
    return equals ? attributeEquals("term", term) : attributeContains("term", term);
  }

  private Specification<Paket> priceContains(String price, Boolean equals) {
    return equals ? attributeEquals("price", price) : attributeContains("price", price);
  }

  private Specification<Paket> freezeTimesContains(String freezeTimes, Boolean equals) {
    return equals ? attributeEquals("freezeTimes", freezeTimes)
        : attributeContains("freezeTimes", freezeTimes);
  }

  private Specification<Paket> freezeDaysContains(String freezeDays, Boolean equals) {
    return equals ? attributeEquals("freezeDays", freezeDays)
        : attributeContains("freezeDays", freezeDays);
  }

  private Specification<Paket> freezeMinTermContains(String freezeMinTerm, Boolean equals) {
    return equals ? attributeEquals("freezeMinTerm", freezeMinTerm)
        : attributeContains("freezeMinTerm", freezeMinTerm);
  }

  private Specification<Paket> accessWithoutCardTimesLimitContains(String accessWithoutCardTimesLimit, Boolean equals) {
    return equals ? attributeEquals("accessWithoutCardTimesLimit", accessWithoutCardTimesLimit)
        : attributeContains("accessWithoutCardTimesLimit", accessWithoutCardTimesLimit);
  }

  private Specification<Paket> autoActivateAfterDaysContains(String autoActivateAfterDays, Boolean equals) {
    return equals ? attributeEquals("autoActivateAfterDays", autoActivateAfterDays)
        : attributeContains("autoActivateAfterDays", autoActivateAfterDays);
  }

  private Specification<Paket> guestVisitsContains(String guestVisits, Boolean equals) {
    return equals ? attributeEquals("guestVisits", guestVisits)
        : attributeContains("guestVisits", guestVisits);
  }

  private Specification<Paket> openDateAllowedContains(String openDateAllowed, Boolean equals) {
    return equals ? attributeEquals("openDateAllowed", openDateAllowed)
        : attributeContains("openDateAllowed", openDateAllowed);
  }

  private Specification<Paket> usersMinContains(String usersMin, Boolean equals) {
    return equals ? attributeEquals("usersMin", usersMin)
        : attributeContains("usersMin", usersMin);
  }

  private Specification<Paket> limitVisitTimeContains(String limitVisitTime, Boolean equals) {
    return equals ? attributeEquals("limitVisitTime", limitVisitTime)
        : attributeContains("limitVisitTime", limitVisitTime);
  }

  private Specification<Paket> visitTimeContains(String visitTime, Boolean equals) {
    return equals ? attributeEquals("visitTime", visitTime)
        : attributeContains("visitTime", visitTime);
  }

  private Specification<Paket> limitAdditionalServicesContains(String limitAdditionalServices, Boolean equals) {
    return equals ? attributeEquals("limitAdditionalServices", limitAdditionalServices)
        : attributeContains("limitAdditionalServices", limitAdditionalServices);
  }

  private Specification<Paket> limitUsageByPaymentPercentageContains(String limitUsageByPaymentPercentage, Boolean equals) {
    return equals ? attributeEquals("limitUsageByPaymentPercentage", limitUsageByPaymentPercentage)
        : attributeContains("limitUsageByPaymentPercentage", limitUsageByPaymentPercentage);
  }

  private Specification<Paket> isActiveContains(String active, Boolean equals) {
    return equals ? attributeEquals("active", active) : attributeContains("active", active);
  }

  private Specification<Paket> purchasableContains(String purchasable, Boolean equals) {
    return equals ? attributeEquals("purchasable", purchasable)
        : attributeContains("purchasable", purchasable);
  }

  private Specification<Paket> attributeContains(String attribute, String value) {
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

  private Specification<Paket> attributeEquals(String attribute, String value) {
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
