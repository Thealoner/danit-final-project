package com.danit.dto.service;

import lombok.Data;

@Data
public abstract class BaseListRequestDto {

  public String search;

  public String id;

  public Boolean equal;

}
