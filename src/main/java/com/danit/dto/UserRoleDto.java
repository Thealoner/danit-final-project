package com.danit.dto;

import com.danit.models.User;
import com.danit.models.UserRolesEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = "users")
@Data
public class UserRoleDto extends BaseDto {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Short.class)
  @Enumerated(value = EnumType.STRING)
  private UserRolesEnum role;

  @JsonView({Views.Extended.class, Views.Ids.class})
  @JsonIgnore
  private List<User> users;

}
