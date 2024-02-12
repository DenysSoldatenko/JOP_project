package com.example.project.exceptions;

/**
 * Exception thrown when a recruiter is not found.
 */
public class RecruiterNotFoundException extends RuntimeException {

  public RecruiterNotFoundException(String message) {
    super(message);
  }
}
