package ua.com.danit.exceptions;

public class EntityNameIsAlreadyExistInDb extends RuntimeException {

  public EntityNameIsAlreadyExistInDb(String message) {
    super(message);
  }

  public EntityNameIsAlreadyExistInDb(String message, Throwable throwable) {
    super(message, throwable);
  }

}
