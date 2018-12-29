package com.danit.dto.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserListRequestDto {

  public String search;

  public Long id;

  public String username;
}
