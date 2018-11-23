package ua.com.danit.exceptions;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
