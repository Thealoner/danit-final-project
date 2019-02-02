package com.danit.exceptions;

public class UserPasswordsNonEqualsException extends RuntimeException {

  public UserPasswordsNonEqualsException(String message) {
    super(message);
  }

  public UserPasswordsNonEqualsException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
