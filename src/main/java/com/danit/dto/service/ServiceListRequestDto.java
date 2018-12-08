package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceListRequestDto {

  public String search;

  public Long id;

  public String title;

  public Float price;

  public Float cost;

  public String unit;

  public int unitsNumber;

  public Boolean isActive;
}
