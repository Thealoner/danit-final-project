package com.danit.dto;

import com.danit.models.UserRoles;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "roles")
@Data
public class UserDto extends BaseDto {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String username;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private Collection<UserRoles> roles;

}
