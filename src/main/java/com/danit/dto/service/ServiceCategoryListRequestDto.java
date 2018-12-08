package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceCategoryListRequestDto {

  public String search;

  public Long id;

  public String title;

  public Boolean isActive;

}
