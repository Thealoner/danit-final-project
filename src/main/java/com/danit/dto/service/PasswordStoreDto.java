package com.danit.dto.service;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
public class PasswordStoreDto {

  private String oldPassword;

  @NonNull
  @Size(min = 5)
  private String newPassword;

  private String token;

}
