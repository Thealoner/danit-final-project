package com.danit.dto;

import com.danit.annotations.TargetClass;
import com.danit.utils.deserializers.CustomBaseEntityListDeserializer;
import com.danit.utils.serializers.CustomBaseEntityListSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "contracts")
@Data
public class PaketDto extends BaseDto {

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
  private Boolean active;

  @JsonView(Views.Extended.class)
  private Boolean purchasable;

  @JsonDeserialize(using = CustomBaseEntityListDeserializer.class)
  @JsonSerialize(using = CustomBaseEntityListSerializer.class)
  @TargetClass(value = ContractDto.class, name = "contracts")
  @JsonView(Views.Extended.class)
  private List<ContractDto> contracts;
}
