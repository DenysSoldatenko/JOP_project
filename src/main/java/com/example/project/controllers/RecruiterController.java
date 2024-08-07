package com.example.project.controllers;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.RecruiterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller class for managing recruiter profile-related endpoints.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/recruiter-profile")
public class RecruiterController {

  private final RecruiterService recruiterService;
  private final SecurityContextHelper securityContextHelper;

  /**
   * Handles GET requests to "/recruiter-profile/" to display the recruiter's profile.
   * Requires authentication using the {@code isAuthenticated} expression.
   *
   * @param model The model to which attributes are added for the view.
   * @return The view name "recruiter_profile" to render the recruiter's profile page.
   * @throws UsernameNotFoundException If the current user's email is not found in the database.
   */
  @GetMapping("/")
  public String recruiterProfile(Model model) {
    User user = securityContextHelper.getCurrentUser();
    Optional<Recruiter> recruiter = recruiterService.findById(user.getId());
    recruiter.ifPresent(r -> model.addAttribute("profile", recruiter));
    return "recruiter_profile";
  }

  /**
   * Handles POST requests to "/recruiter-profile/addNew" to add a new recruiter profile.
   *
   * @param recruiter The {@link Recruiter} entity to be added.
   * @param model The {@link Model} to which attributes are added for the view.
   * @param multipartFile The {@link MultipartFile} containing the profile photo.
   * @return A redirect to the dashboard page upon successful addition of the recruiter profile.
   */
  @PostMapping("/addNew")
  public String addRecruiterProfile(Recruiter recruiter, Model model,
                                    @RequestParam("image") MultipartFile multipartFile) {
    User user = securityContextHelper.getCurrentUser();
    model.addAttribute("profile", recruiter);
    recruiterService.createRecruiter(user, recruiter, multipartFile);
    return "redirect:/dashboard";
  }
}