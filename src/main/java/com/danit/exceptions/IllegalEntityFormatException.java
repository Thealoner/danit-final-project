package com.danit.exceptions;

public class IllegalEntityFormatException extends RuntimeException {

  public IllegalEntityFormatException(String message) {
    super(message);
  }

  public IllegalEntityFormatException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
