package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContractListRequestDto {

  public String search;

  public String id;

  public String paketId;

  public String clientGender;

  public String startDate;

  public String endDate;

  public String credit;

  public String clientId;

  private String active;
}
