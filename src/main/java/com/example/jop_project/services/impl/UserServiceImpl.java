package com.example.jop_project.services.impl;

import static java.lang.System.currentTimeMillis;

import com.example.jop_project.entities.User;
import com.example.jop_project.repositories.UserRepository;
import com.example.jop_project.services.UserService;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface.
 * This service provides methods for creating and retrieving {@link User} entities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  /**
   * Creates a new user in the system.
   *
   * @param user The {@link User} object representing the user to be created.
   */
  public void createUser(User user) {
    user.setEmail(user.getEmail());
    user.setPassword(user.getPassword());
    user.setActive(true);
    user.setUserTypeId(user.getUserTypeId());
    user.setRegistrationDate(new Date(currentTimeMillis()));
    userRepository.save(user);
  }

  /**
   * Retrieves a user by their email address.
   *
   * @param email The email address of the user to retrieve.
   * @return An {@link Optional} containing the {@link User} entity, if found.
   *         If no user is found with the specified email, an empty {@link Optional} is returned.
   */
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
