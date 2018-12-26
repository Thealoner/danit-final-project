package com.danit.dto;

import com.danit.models.User;
import com.danit.models.UserRolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(value = {"users"}, allowSetters = true, ignoreUnknown = true)
@ToString(exclude = "users")
@Data
public class UserRoleDto extends BaseDto {

  @JsonView(Views.Short.class)
  @Enumerated(value = EnumType.STRING)
  private UserRolesEnum role;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<User> users;

}
