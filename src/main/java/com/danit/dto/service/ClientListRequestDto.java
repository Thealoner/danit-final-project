package com.danit.dto.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class ClientListRequestDto extends BaseListRequestDto {

  public String firstName;

  public String lastName;

  public String gender;

  public String birthDate;

  public String phoneNumber;

  public String email;

  public String active;

}
