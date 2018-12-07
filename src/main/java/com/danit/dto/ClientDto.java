package com.danit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "contracts")
@Data
public class ClientDto extends BaseDto {

  @JsonView({Views.Ids.class, Views.Short.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String firstName;

  @JsonView(Views.Short.class)
  private String lastName;

  @JsonView(Views.Short.class)
  private String gender;

  @JsonView(Views.Short.class)
  private Date birthDate;

  @JsonView(Views.Short.class)
  private String phoneNumber;

  @JsonView(Views.Short.class)
  private String email;

  @JsonView(Views.Short.class)
  private Boolean active;

  @JsonView({Views.Extended.class, Views.Ids.class})
  private List<ContractDto> contracts;

}
