package com.danit.exceptions.handlers;

import lombok.Data;

import java.util.Date;

@Data
class ErrorDetails {

  private Date timestamp;
  private String message;
  private String details;

}
