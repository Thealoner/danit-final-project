package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
public class ContractListRequestDto {

  public String search;

  public String startDate;

  public Date endDate;

  public Float credit;

  public ClientListRequestDto client;

  //  private PaketDto paket;

  private Boolean active;
}
