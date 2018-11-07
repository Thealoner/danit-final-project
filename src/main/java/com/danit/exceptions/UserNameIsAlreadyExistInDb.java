package com.danit.exceptions;

public class UserNameIsAlreadyExistInDb extends RuntimeException {

  public UserNameIsAlreadyExistInDb(String message) {
    super(message);
  }

  public UserNameIsAlreadyExistInDb(String message, Throwable throwable) {
    super(message, throwable);
  }

}
