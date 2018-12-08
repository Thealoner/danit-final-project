package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceCategoryListRequestDto {

  String search;

  private Long id;

  private String title;

  private Boolean isActive;

}
