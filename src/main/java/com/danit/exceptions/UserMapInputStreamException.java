package com.danit.exceptions;

public class UserMapInputStreamException extends RuntimeException {

    public UserMapInputStreamException(String message) {
      super(message);
    }

    public UserMapInputStreamException(String message, Throwable throwable) {
      super(message, throwable);
    }

}
