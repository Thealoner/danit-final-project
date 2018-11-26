package com.danit.exceptions;

public class EntityParticularDataException extends RuntimeException {

  public EntityParticularDataException(String message) {
    super(message);
  }

  public EntityParticularDataException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
