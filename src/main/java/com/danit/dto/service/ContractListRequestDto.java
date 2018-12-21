package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class ContractListRequestDto {

  public String search;

  public String paketId;
  public String clientGender;

  public String startDate;

  public Date endDate;

  public Float credit;

  public ClientListRequestDto client;

  public Long clientId;

  private Boolean active;
}
