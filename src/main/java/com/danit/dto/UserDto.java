package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "roles")
@Data
public class UserDto extends BaseDto {

  @JsonView(Views.Short.class)
  private String username;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<UserRoleDto> roles;

}
