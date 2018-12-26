package com.danit.dto;

import com.danit.models.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = {"client"}, allowSetters = true, ignoreUnknown = true)
@ToString(exclude = "cards")
@Data
public class ContractDto extends BaseDto {

  @JsonView(Views.Short.class)
  private Date startDate;

  @JsonView(Views.Short.class)
  private Date endDate;

  @JsonView(Views.Short.class)
  private Float credit;

  @JsonView(Views.Short.class)
  private Long packageId;

  @JsonView(Views.Short.class)
  private Long clientId;

  private Client client;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<CardDto> cards;

}
