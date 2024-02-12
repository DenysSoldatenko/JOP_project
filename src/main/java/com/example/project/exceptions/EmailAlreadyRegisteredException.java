package com.example.project.exceptions;

/**
 * Exception thrown when attempting to register an email address that is already registered.
 */
public class EmailAlreadyRegisteredException extends RuntimeException {

  public EmailAlreadyRegisteredException(String message) {
    super(message);
  }
}
