package com.danit.dto.service;


import com.danit.models.UserRolesEnum;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class UserRoleListRequestDto {

  public String search;

  public Long id;

  public UserRolesEnum role;

}
