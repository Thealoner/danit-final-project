package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClientListRequestDto {

  public String search;

  public String firstName;

  public String lastName;

  public String gender;

  public Date birthDate;

  public String phoneNumber;

  public String email;

  public Boolean active;

}
