package com.example.project.security;

import static com.example.project.utils.ErrorMessages.USER_NOT_FOUND;

import com.example.project.entities.User;
import com.example.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of the {@link UserDetailsService} interface to load user-specific data.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = usersRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}