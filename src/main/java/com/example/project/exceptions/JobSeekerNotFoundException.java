package com.example.project.exceptions;

/**
 * Exception thrown when a job seeker is not found.
 */
public class JobSeekerNotFoundException extends RuntimeException {

  public JobSeekerNotFoundException(String message) {
    super(message);
  }
}
