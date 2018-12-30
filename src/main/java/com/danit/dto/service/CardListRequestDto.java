package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CardListRequestDto {

  public String search;

  public Long id;

  public String code;

  public Boolean active;

}
