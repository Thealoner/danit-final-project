package com.danit.dto;

import com.danit.dto.service.PaketDto;
import com.danit.utils.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@ToString(exclude = "client")
public class ContractDto {

  @JsonView(Views.Extended.class)
  private Long id;

  @JsonView(Views.Short.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date startDate;

  @JsonView(Views.Short.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
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
