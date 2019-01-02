package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PaketListRequestDto extends BaseListRequestDto {

  public String title;

  public String term;

  public String price;

  public String freezeTimes;

  public String freezeDays;

  public String freezeMinTerm;

  public String accessWithoutCardTimesLimit;

  public String autoActivateAfterDays;

  public String guestVisits;

  public String openDateAllowed;

  public String usersMin;

  public String limitVisitTime;

  public String visitTime;

  public String limitAdditionalServices;

  public String limitUsageByPaymentPercentage;

  public String active;

  public String purchasable;

}
