package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class PaketListRequestDto {

  public String search;

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

  public Boolean isActive;

  public Boolean purchasable;

}
