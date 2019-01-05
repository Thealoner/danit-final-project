package com.danit.dto.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class ServiceListRequestDto extends BaseListRequestDto {

  public String title;

  public String price;

  public String cost;

  public String unit;

  public String unitsNumber;

  public String active;

  public String serviceCategoryId;
}
