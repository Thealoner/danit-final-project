package com.danit.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceDto extends BaseDto {

  private Long id;

  private String title;

  private Float price;

  private Float cost;

  private String unit;

  private int unitsNumber;

  private List<ServiceCategoryDto> serviceCategories;

  private Boolean isActive;
}
