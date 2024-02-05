package com.example.jop_project.controllers;

import com.example.jop_project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for managing job post activity related endpoints.
 */
@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

  private final UserService userService;

  @GetMapping("/dashboard")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUsername = authentication.getName();
      model.addAttribute("username", currentUsername);
    }

    model.addAttribute("user", currentUserProfile);
    return "dashboard";
  }
}