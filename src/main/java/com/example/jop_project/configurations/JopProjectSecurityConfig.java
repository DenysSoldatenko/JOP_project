package com.example.jop_project.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up the security configurations for the application.
 */
@Configuration
public class JopProjectSecurityConfig {

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
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(auth -> {
      auth.requestMatchers(publicUrl).permitAll();
      auth.anyRequest().authenticated();
    });

    return http.build();
  }
}
