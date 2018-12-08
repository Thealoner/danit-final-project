package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceListRequestDto {

  String search;

  private Long id;

  private String title;

  private Float price;

  private Float cost;

  private String unit;

  private int unitsNumber;

  private Boolean isActive;
}
