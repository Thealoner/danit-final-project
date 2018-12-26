package com.danit.exceptions;

public class ObjectToJsonProcessingException extends RuntimeException {

  public ObjectToJsonProcessingException(String message) {
    super(message);
  }

  public ObjectToJsonProcessingException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
