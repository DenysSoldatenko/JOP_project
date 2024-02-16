package com.example.project.exceptions;

/**
 * Exception thrown when a job is not found.
 */
public class JobNotFoundException extends RuntimeException {

  public JobNotFoundException(String message) {
    super(message);
  }
}
