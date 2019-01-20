package com.danit.dto.service;

import lombok.Data;

@Data
public class PasswordStoreDto {

  private String oldPassword;

  private String newPassword;

  private String token;

}
