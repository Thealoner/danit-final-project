package com.danit.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
class ErrorDetails {

  private Date timestamp;
  private String message;
  private String details;

}
