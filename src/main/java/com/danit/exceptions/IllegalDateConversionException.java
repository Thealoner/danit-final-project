package com.danit.exceptions;

public class IllegalDateConversionException extends RuntimeException {

  public IllegalDateConversionException(String message) {
    super(message);
  }

  public IllegalDateConversionException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
