package com.danit.dto;

import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "cards")
@Data
public class ContractDto extends BaseDto {

  @JsonView(Views.Extended.class)
  private Long id;

  @JsonView(Views.Short.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date startDate;

  @JsonView(Views.Short.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date endDate;

  @JsonView(Views.Extended.class)
  private Float credit;

  //  @JsonView(Views.Extended.class)
  //  @JsonIgnore
  //  private ClientDto client;
  //
  //  @JsonView(Views.Extended.class)
  //  @JsonIgnore
  //  private PaketDto paket;

  @JsonView(Views.Extended.class)
  private List<CardColorDto> cards;

  @JsonView(Views.Extended.class)
  private Long packageId;

  @JsonView(Views.Extended.class)
  private Long clientId;

  @JsonView(Views.Short.class)
  private Boolean active;

}
