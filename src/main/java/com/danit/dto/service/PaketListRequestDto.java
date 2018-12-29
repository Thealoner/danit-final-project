package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PaketListRequestDto {

  public String search;

  public String id;

  public String title;

  public Integer term;

  public Float price;

  public Integer freezeTimes;

  public Integer freezeDays;

  public Integer freezeMinTerm;

  public Integer accessWithoutCardTimesLimit;

  public Integer autoActivateAfterDays;

  public Integer guestVisits;

  public Boolean openDateAllowed;

  public Integer usersMin;

  public Boolean limitVisitTime;

  public Integer visitTime;

  public Boolean limitAdditionalServices;

  public Boolean limitUsageByPaymentPercentage;

  public Boolean active;

  public Boolean purchasable;

}
