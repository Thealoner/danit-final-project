package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @JsonView(Views.Short.class)
  private String password;

  @JsonView(Views.Short.class)
  private String email;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<UserRoleDto> roles;

}
