package com.danit.dto.service;

import com.danit.dto.ContractDto;
import com.danit.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class PaketDto {

  @JsonView({Views.Extended.class, Views.Ids.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String title;

  @JsonView(Views.Short.class)
  private int term;

  @JsonView(Views.Extended.class)
  private Float price;

  @JsonView(Views.Extended.class)
  private int freezeTimes;

  @JsonView(Views.Extended.class)
  private int freezeDays;

  @JsonView(Views.Extended.class)
  private int freezeMinTerm;

  @JsonView(Views.Extended.class)
  private int accessWithoutCardTimesLimit;

  @JsonView(Views.Extended.class)
  private int autoActivateAfterDays;

  @JsonView(Views.Extended.class)
  private int guestVisits;

  @JsonView(Views.Extended.class)
  private Boolean openDateAllowed;

  @JsonView(Views.Extended.class)
  private int usersMin;

  @JsonView(Views.Extended.class)
  private Boolean limitVisitTime;

  @JsonView(Views.Extended.class)
  private int visitTime;

  @JsonView(Views.Extended.class)
  private Boolean limitAdditionalServices;

  @JsonView(Views.Extended.class)
  private Boolean limitUsageByPaymentPercentage;

  @JsonView(Views.Short.class)
  private Boolean isActive;

  @JsonView(Views.Extended.class)
  private Boolean purchasable;

  @JsonView(Views.Extended.class)
  private List<ContractDto> contracts;
}
