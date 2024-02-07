package com.example.project.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
  private final CustomAuthSuccessHandler customAuthSuccessHandler;

  private static final String[] PUBLIC_ROUTES = {
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
   * Defines the {@link AuthenticationManager} bean for handling authentication requests.
   *
   * @param http The {@link HttpSecurity} object for configuring authentication manager builder.
   * @return The {@link AuthenticationManager} bean.
   * @throws Exception If an error occurs while configuring the authentication manager builder.
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder
        = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(customUserDetailsService)
        .passwordEncoder(passwordEncoder());
    return authenticationManagerBuilder.build();
  }

  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(PUBLIC_ROUTES).permitAll()
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")
        .permitAll()
        .successHandler(customAuthSuccessHandler)
      )
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/")
      )
      .cors(withDefaults())
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }
}
