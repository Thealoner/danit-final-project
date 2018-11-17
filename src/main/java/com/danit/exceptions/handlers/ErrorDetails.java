package com.danit.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
class ErrorDetails {

  private Date timestamp; // NOSONAR
  private String message; // NOSONAR
  private String details; // NOSONAR

}
