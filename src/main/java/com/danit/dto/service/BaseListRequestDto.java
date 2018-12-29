package com.danit.dto.service;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class BaseListRequestDto {

  public String search;

  public String id;

  public Boolean equals;

}
