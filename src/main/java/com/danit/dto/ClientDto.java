package com.danit.dto;

import com.danit.utils.CustomDateDeserializer;
import com.danit.utils.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "contracts")
@Data
public class ClientDto extends BaseDto {

  @JsonView({Views.Extended.class, Views.Ids.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String firstName;

  @JsonView(Views.Short.class)
  private String lastName;

  @JsonView(Views.Short.class)
  private String gender;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateDeserializer.class)
  @JsonSerialize(using = CustomDateSerializer.class)
  private Date birthDate;

  @JsonView(Views.Extended.class)
  private String phoneNumber;

  @JsonView(Views.Extended.class)
  private String email;

  @JsonView(Views.Extended.class)
  private Boolean active;

  @JsonView(Views.Extended.class)
  private List<ContractDto> contracts;

}
