package com.example.jop_project.services;

import com.example.jop_project.entities.User;
import java.util.Optional;

/**
 * Service interface for managing {@link User} entities.
 */
public interface UserService {

  void createUser(User user);

  Optional<User> getUserByEmail(String email);

  Object getCurrentUserProfile();
}
