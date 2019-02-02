package com.danit.dto;

import com.danit.utils.CustomListBaseEntityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

  @JsonDeserialize(using = CustomListBaseEntityDeserializer.class)
  @JsonProperty(value = "roles", access = JsonProperty.Access.WRITE_ONLY)
  private Long[] rolesIncoming;

  @JsonView({Views.Extended.class, Views.Ids.class})
  @JsonProperty(value = "roles", access = JsonProperty.Access.READ_ONLY)
  private List<UserRoleDto> roles;

}
