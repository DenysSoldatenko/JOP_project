package com.example.project.controllers;

import com.example.project.security.SecurityContextHelper;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
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
  private final SecurityContextHelper securityContextHelper;

  @GetMapping("/dashboard")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    String currentUsername = securityContextHelper.getCurrentUser().getEmail();
    model.addAttribute("username", currentUsername);
    model.addAttribute("user", currentUserProfile);
    return "dashboard";
  }
}