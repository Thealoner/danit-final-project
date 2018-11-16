package com.danit.dto.clientdto;

import com.danit.models.Contract;
import com.danit.utils.CustomDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;
import java.util.List;

public class ClientDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String gender;

  @JsonDeserialize(using = CustomDateAndTimeDeserialize.class)
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd")
  private Date birthDate;

  private String phoneNumber;

  private String email;

  private Boolean active;

  private List<Contract> contracts;
}
