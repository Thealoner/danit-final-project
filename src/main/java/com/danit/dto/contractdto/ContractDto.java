package com.danit.dto.contractdto;

import com.danit.dto.clientdto.ClientDto;
import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

@Data
public class ContractDto {

  private Long id;

  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd")
  private Date startDate;

  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
    shape = JsonFormat.Shape.STRING,
    pattern = "yyyy-MM-dd")
  private Date endDate;
  private Float credit;
  private boolean isActive;

  private ClientDto client;
//  private Paket paket;
//  private List<CardColor> cards;
//  private Long packageId;
  private Long clientId;
}
