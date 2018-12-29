package com.danit.dto.service;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseListRequestDto {

  public String search;

  public String id;

  public Boolean equals;

}
