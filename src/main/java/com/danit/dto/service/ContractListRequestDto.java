package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ContractListRequestDto extends BaseListRequestDto {

  public String paketId;

  public String clientGender;

  public String startDate;

  public String endDate;

  public String credit;

  public String clientId;

  public String active;
}
