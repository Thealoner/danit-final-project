package com.danit.dto.service;

import lombok.Data;

@Data
public class ServiceCategoryListRequestDto {

  public String search;

  public String title;

  public Boolean active;

}
