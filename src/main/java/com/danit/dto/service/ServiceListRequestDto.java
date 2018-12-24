package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceListRequestDto {

  public String search;

  public String title;

  public String price;

  public String cost;

  public String unit;

  public String unitsNumber;

  public Boolean active;

  public String serviceCategoryId;
}
