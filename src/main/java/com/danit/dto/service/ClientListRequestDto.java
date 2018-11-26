package com.danit.dto.service;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
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
