package com.example.jop_project.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up the security configurations for the application.
 */
@Configuration
@RequiredArgsConstructor
public class JopProjectSecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  private final String[] publicUrl = {
    "/",
    "/global-search/**",
    "/register",
    "/register/**",
    "/webjars/**",
    "/resources/**",
    "/assets/**",
    "/css/**",
    "/summernote/**",
    "/js/**",
    "/*.css",
    "/*.js",
    "/*.js.map",
    "/fonts**",
    "/favicon.ico",
    "/resources/**",
    "/error"
  };

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the authentication provider with
   * a custom user details service and a password encoder.
   *
   * @return An {@link AuthenticationProvider} configured
   *      with custom user details service and password encoder.
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    return authenticationProvider;
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authenticationProvider(authenticationProvider());

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(publicUrl).permitAll();
      auth.anyRequest().authenticated();
    });

    return http.build();
  }
}
