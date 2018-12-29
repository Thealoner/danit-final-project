package com.danit.dto.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClientListRequestDto extends BaseListRequestDto {

  public String firstName;

  public String lastName;

  public String gender;

  public Date birthDate;

  public String phoneNumber;

  public String email;

  public Boolean active;

}
