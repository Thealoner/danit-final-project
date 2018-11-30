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
    return (root, query, cb) -> {
      query.distinct(true);
      return where(
          where(titleContains(request.search))
              .or(termContains(request.search))
              .or(priceContains(request.search))
              .or(freezeTimesContains(request.search))
              .or(freezeDaysContains(request.search))
              .or(freezeMinTermContains(request.search))
              .or(accessWithoutCardTimesLimitContains(request.search))
              .or(autoActivateAfterDaysContains(request.search))
              .or(guestVisitsContains(request.search))
              .or(openDateAllowedContains(request.search))
              .or(usersMinContains(request.search))
              .or(limitVisitTimeContains(request.search))
              .or(visitTimeContains(request.search))
              .or(limitAdditionalServicesContains(request.search))
              .or(limitUsageByPaymentPercentageContains(request.search))
              .or(isActiveContains(request.search))
              .or(purchasableContains(request.search))

      )
          .and(titleContains(request.title))
          .and(termContains(request.term))
          .and(priceContains(request.price))
          .and(freezeTimesContains(request.freezeTimes))
          .and(freezeDaysContains(request.freezeDays))
          .and(freezeMinTermContains(request.freezeMinTerm))
          .and(accessWithoutCardTimesLimitContains(request.accessWithoutCardTimesLimit))
          .and(autoActivateAfterDaysContains(request.autoActivateAfterDays))
          .and(guestVisitsContains(request.guestVisits))
          .and(openDateAllowedContains(request.openDateAllowed))
          .and(usersMinContains(request.usersMin))
          .and(limitVisitTimeContains(request.limitVisitTime))
          .and(visitTimeContains(request.visitTime))
          .and(limitAdditionalServicesContains(request.limitAdditionalServices))
          .and(limitUsageByPaymentPercentageContains(request.limitUsageByPaymentPercentage))
          .and(isActiveContains(request.isActive))
          .and(purchasableContains(request.purchasable))
          .toPredicate(root, query, cb);
    };
  }

  private Specification<Paket> titleContains(String title) {
    return attributeContains("title", title);
  }

  private Specification<Paket> termContains(Object term) {
    return Objects.nonNull(term) ? attributeContains("term", String.valueOf(term)) :
        null;
  }

  private Specification<Paket> priceContains(Object price) {
    return Objects.nonNull(price) ? attributeContains("price", String.valueOf(price)) :
        null;
  }

  private Specification<Paket> freezeTimesContains(Object freezeTimes) {
    return Objects.nonNull(freezeTimes) ? attributeContains("freezeTimes", String.valueOf(freezeTimes)) :
        null;
  }

  private Specification<Paket> freezeDaysContains(Object freezeDays) {
    return Objects.nonNull(freezeDays) ? attributeContains("freezeDays", String.valueOf(freezeDays)) :
        null;
  }

  private Specification<Paket> freezeMinTermContains(Object freezeMinTerm) {
    return Objects.nonNull(freezeMinTerm) ? attributeContains("freezeMinTerm", String.valueOf(freezeMinTerm)) :
        null;
  }

  private Specification<Paket> accessWithoutCardTimesLimitContains(Object accessWithoutCardTimesLimit) {
    return Objects.nonNull(accessWithoutCardTimesLimit) ? attributeContains("accessWithoutCardTimesLimit",
        String.valueOf(accessWithoutCardTimesLimit)) :
        null;
  }

  private Specification<Paket> autoActivateAfterDaysContains(Object autoActivateAfterDays) {
    return Objects.nonNull(autoActivateAfterDays) ? attributeContains("autoActivateAfterDays",
        String.valueOf(autoActivateAfterDays)) :
        null;
  }

  private Specification<Paket> guestVisitsContains(Object guestVisits) {
    return Objects.nonNull(guestVisits) ? attributeContains("guestVisits",
        String.valueOf(guestVisits)) :
        null;
  }

  private Specification<Paket> openDateAllowedContains(Object openDateAllowed) {
    return Objects.nonNull(openDateAllowed) ? attributeContains("openDateAllowed",
        String.valueOf(openDateAllowed)) :
        null;
  }

  private Specification<Paket> usersMinContains(Object usersMin) {
    return Objects.nonNull(usersMin) ? attributeContains("usersMin",
        String.valueOf(usersMin)) :
        null;
  }

  private Specification<Paket> limitVisitTimeContains(Object limitVisitTime) {
    return Objects.nonNull(limitVisitTime) ? attributeContains("limitVisitTime",
        String.valueOf(limitVisitTime)) :
        null;
  }

  private Specification<Paket> visitTimeContains(Object visitTime) {
    return Objects.nonNull(visitTime) ? attributeContains("visitTime",
        String.valueOf(visitTime)) :
        null;
  }

  private Specification<Paket> limitAdditionalServicesContains(Object limitAdditionalServices) {
    return Objects.nonNull(limitAdditionalServices) ? attributeContains("limitAdditionalServices",
        String.valueOf(limitAdditionalServices)) :
        null;
  }

  private Specification<Paket> limitUsageByPaymentPercentageContains(Object limitUsageByPaymentPercentage) {
    return Objects.nonNull(limitUsageByPaymentPercentage) ? attributeContains("limitUsageByPaymentPercentage",
        String.valueOf(limitUsageByPaymentPercentage)) :
        null;
  }

  private Specification<Paket> isActiveContains(Object isActive) {
    return Objects.nonNull(isActive) ? attributeContains("isActive",
        String.valueOf(isActive)) :
        null;
  }

  private Specification<Paket> purchasableContains(Object purchasable) {
    return Objects.nonNull(purchasable) ? attributeContains("purchasable",
        String.valueOf(purchasable)) :
        null;
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
}
