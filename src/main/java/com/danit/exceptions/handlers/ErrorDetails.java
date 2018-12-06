package com.danit.exceptions.handlers;

import com.danit.utils.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
class ErrorDetails {

  @JsonSerialize(using = CustomDateTimeSerializer.class)
  private Date timestamp; // NOSONAR

  private String message; // NOSONAR

  private String details; // NOSONAR

}
