package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "cards")
@Data
public class ContractDto extends BaseDto {

  @JsonView(Views.Ids.class)
  private Long id;

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

  @JsonView(Views.Short.class)
  private Boolean active;

  //  @JsonView(Views.Extended.class)
  //  @JsonIgnore
  //  private ClientDto client;
  //
  //  @JsonView(Views.Extended.class)
  //  @JsonIgnore
  //  private PaketDto paket;

  @JsonView(Views.Extended.class)
  private List<CardDto> cards;


}
