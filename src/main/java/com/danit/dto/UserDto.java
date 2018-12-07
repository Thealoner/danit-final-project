package com.danit.dto;

import com.danit.models.UserRoles;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "roles")
@Data
public class UserDto extends BaseDto {
  private Long id;

  private String username;

  //  private String password;

  private Collection<UserRoles> roles;

}
