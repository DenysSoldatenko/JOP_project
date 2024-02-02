package com.example.jop_project.services.impl;

import static java.lang.System.currentTimeMillis;

import com.example.jop_project.entities.JobSeeker;
import com.example.jop_project.entities.Recruiter;
import com.example.jop_project.entities.User;
import com.example.jop_project.repositories.JobSeekerRepository;
import com.example.jop_project.repositories.RecruiterRepository;
import com.example.jop_project.repositories.UserRepository;
import com.example.jop_project.services.UserService;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface.
 * This service provides methods for creating and retrieving {@link User} entities.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RecruiterRepository recruiterRepository;
  private final JobSeekerRepository jobSeekerRepository;

  /**
   * Creates a new user in the system.
   *
   * @param user The {@link User} object representing the user to be created.
   */
  @Transactional
  public void createUser(User user) {
    user.setActive(true);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRegistrationDate(new Date(currentTimeMillis()));
    User savedUser = userRepository.save(user);

    if (user.getUserType().getUserTypeId() == 1) {
      Recruiter recruiter = new Recruiter();
      recruiter.setUser(savedUser);
      recruiterRepository.save(recruiter);
    } else {
      JobSeeker jobSeeker = new JobSeeker();
      jobSeeker.setUser(savedUser);
      jobSeekerRepository.save(jobSeeker);
    }
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
