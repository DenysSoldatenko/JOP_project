package com.example.project.controllers;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

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

  @GetMapping("/dashboard")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    String currentUsername = getContext().getAuthentication().getName();
    model.addAttribute("username", currentUsername);
    model.addAttribute("user", currentUserProfile);
    return "dashboard";
  }
}