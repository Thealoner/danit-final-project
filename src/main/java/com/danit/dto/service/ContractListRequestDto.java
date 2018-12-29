package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ContractListRequestDto {

  public String search;

  public String paketId;

  public String clientGender;

  public String startDate;

  public String endDate;

  public String credit;

  public String clientId;

  private String active;
}
