package com.danit.dto.service;


import com.danit.models.UserRolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserRoleListRequestDto {

  public String search;

  public Long id;

  public UserRolesEnum role;

}
