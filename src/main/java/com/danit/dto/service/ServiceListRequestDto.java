package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
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
