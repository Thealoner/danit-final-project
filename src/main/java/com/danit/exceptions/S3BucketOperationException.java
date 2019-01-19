package com.danit.exceptions;

public class S3BucketOperationException extends RuntimeException {

  public S3BucketOperationException(String message) {
    super(message);
  }

  public S3BucketOperationException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
