package com.example.project.security;

import static com.example.project.utils.ErrorMessages.USER_NOT_FOUND;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.example.project.entities.User;
import com.example.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Helper class to manage security context operations.
 */
@Component
@RequiredArgsConstructor
public class SecurityContextHelper {

  private final UserRepository userRepository;

  /**
   * Retrieves the currently authenticated user from the security context.
   *
   * @return The {@link User} object representing the current authenticated user.
   * @throws UsernameNotFoundException If the current user's email is not found in the database.
   */
  public User getCurrentUser() {
    String currentUsername = getContext().getAuthentication().getName();
    return userRepository.findByEmail(currentUsername)
      .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + currentUsername));
  }
}
