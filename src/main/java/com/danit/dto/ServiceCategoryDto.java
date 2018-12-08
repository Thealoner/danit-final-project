package com.danit.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceCategoryDto extends BaseDto {
  private Long id;

  private String title;

  private Boolean isActive;

  private List<ServiceDto> services;
}
