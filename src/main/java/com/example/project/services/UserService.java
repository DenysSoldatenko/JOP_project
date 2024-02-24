package com.example.project.services;

import com.example.project.entities.User;

/**
 * Service interface for managing {@link User} entities.
 */
public interface UserService {

  void createUser(User user);

  Object getCurrentUserProfile();
}
