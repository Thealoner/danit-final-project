package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ServiceListRequestDto extends BaseListRequestDto{

  public String title;

  public String price;

  public String cost;

  public String unit;

  public String unitsNumber;

  public String active;

  public String serviceCategoryId;
}
