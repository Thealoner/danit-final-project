package com.danit.dto.clientdto;

import com.danit.models.Contract;
import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

public class ClientDto {

  @Null
  private Long id;

  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private String gender;

  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date birthDate;

  @NotNull
  private String phoneNumber;

  @NotNull
  private String email;

  @NotNull
  private Boolean active;

  @NotNull
  private List<Contract> contracts;
}
