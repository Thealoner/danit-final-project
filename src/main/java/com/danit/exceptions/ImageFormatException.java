package com.danit.exceptions;

public class ImageFormatException extends RuntimeException {

  public ImageFormatException(String message) {
    super(message);
  }

  public ImageFormatException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
