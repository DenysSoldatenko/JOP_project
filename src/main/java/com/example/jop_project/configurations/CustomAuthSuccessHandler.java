package com.example.jop_project.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Custom authentication success handler to handle post-authentication actions.
 */
@Slf4j
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    log.info("\n The username {} is logged in. \n", username);
    boolean hasRole = authentication.getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().equals("Job Seeker")
            || r.getAuthority().equals("Recruiter"));

    if (hasRole) {
      response.sendRedirect("/dashboard/");
    }
  }
}