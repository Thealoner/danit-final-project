package com.danit.dto.service;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserListRequestDto {

  public String search;

  public Long id;

  public String username;
}
