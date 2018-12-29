package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
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
