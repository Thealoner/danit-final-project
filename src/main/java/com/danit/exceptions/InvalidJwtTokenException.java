package com.danit.exceptions;

public class InvalidJwtTokenException extends RuntimeException {

  public InvalidJwtTokenException(String message) {
    super(message);
  }

  public InvalidJwtTokenException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
