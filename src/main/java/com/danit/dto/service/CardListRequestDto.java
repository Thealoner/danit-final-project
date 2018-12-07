package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CardListRequestDto {

  public String search;

  public Long id;

  public String code;

  public Boolean active;

}
