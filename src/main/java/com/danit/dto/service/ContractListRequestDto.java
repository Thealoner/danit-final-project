package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContractListRequestDto {

  public String search;

  public String startDate;

  public Date endDate;

  public Float credit;

  public ClientListRequestDto client;

  public Long clientId;

  public Long paketId;

  private Boolean active;
}
