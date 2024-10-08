package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.EMAIL_ALREADY_REGISTERED;
import static com.example.project.utils.ErrorMessages.USER_NOT_FOUND;
import static java.lang.System.currentTimeMillis;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import com.example.project.exceptions.EmailAlreadyRegisteredException;
import com.example.project.exceptions.UserNotFoundException;
import com.example.project.repositories.JobSeekerRepository;
import com.example.project.repositories.RecruiterRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.services.UserService;
import jakarta.transaction.Transactional;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    userRepository.findByEmail(user.getEmail())
        .ifPresent(existingUser -> {
          throw new EmailAlreadyRegisteredException(EMAIL_ALREADY_REGISTERED);
        });

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

  @Override
  public Object getCurrentUserProfile() {
    Authentication authentication = getContext().getAuthentication();
    String username = getContext().getAuthentication().getName();
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + username));
    int userId = user.getId();

    if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
      return recruiterRepository.findById(userId).orElse(new Recruiter());
    } else {
      return jobSeekerRepository.findById(userId).orElse(new JobSeeker());
    }
  }
}
