package com.danit.dto;

import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
public class ClientDto {

  @JsonView({Views.Extended.class, Views.Ids.class})
  private Long id;

  @JsonView(Views.Short.class)
  private String firstName;

  @JsonView(Views.Short.class)
  private String lastName;

  @JsonView(Views.Short.class)
  private String gender;

  @JsonView(Views.Extended.class)
  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
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
