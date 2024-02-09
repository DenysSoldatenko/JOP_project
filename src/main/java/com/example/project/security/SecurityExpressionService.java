package com.example.project.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class for security-related expressions and checks.
 */
@Slf4j
@Service("expressionService")
public class SecurityExpressionService {

  /**
   * Checks if the current user is authenticated.
   *
   * @return {@code true} if the user is authenticated, {@code false} otherwise.
   */
  public static boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isAuthenticated = authentication != null
        && !(authentication instanceof AnonymousAuthenticationToken);
    log.info("\n User authenticated: {} \n", isAuthenticated);
    return isAuthenticated;
  }
}
