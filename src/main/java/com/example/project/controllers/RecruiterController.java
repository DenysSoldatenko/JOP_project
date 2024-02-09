package com.example.project.controllers;

import static com.example.project.utils.ErrorMessages.USER_NOT_FOUND;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import com.example.project.repositories.UserRepository;
import com.example.project.services.RecruiterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing recruiter profile-related endpoints.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recruiter-profile")
public class RecruiterController {

  private final UserRepository userRepository;
  private final RecruiterService recruiterService;

  /**
   * Handles GET requests to "/recruiter-profile/" to display the recruiter's profile.
   * Requires authentication using the {@code isAuthenticated} expression.
   *
   * @param model The model to which attributes are added for the view.
   * @return The view name "recruiter_profile" to render the recruiter's profile page.
   * @throws UsernameNotFoundException If the current user's email is not found in the database.
   */
  @GetMapping("/")
  @PreAuthorize("@expressionService.isAuthenticated()")
  public String recruiterProfile(Model model) {
    String currentUsername = getContext().getAuthentication().getName();
    User user = userRepository.findByEmail(currentUsername)
        .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    Optional<Recruiter> recruiter = recruiterService.findById(user.getId());
    recruiter.ifPresent(r -> model.addAttribute("profile", recruiter));

    return "recruiter_profile";
  }
}