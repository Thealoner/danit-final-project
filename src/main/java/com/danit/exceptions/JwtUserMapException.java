package com.danit.exceptions;

public class JwtUserMapException extends RuntimeException {

  public JwtUserMapException(String message) {
    super(message);
  }

  public JwtUserMapException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
