package ua.com.danit.exceptions;

public class IllegalAccessReflectionException extends RuntimeException {

  public IllegalAccessReflectionException(String message) {
    super(message);
  }

  public IllegalAccessReflectionException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
