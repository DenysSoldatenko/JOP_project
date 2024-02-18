package com.example.project.controllers;

import com.example.project.entities.PostActivity;
import com.example.project.services.PostActivityService;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class for handling job seeker applications and related actions.
 */
@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {

  private final PostActivityService postActivityService;
  private final UserService usersService;

  @GetMapping("job-details-apply/{id}")
  public String showJobDetails(@PathVariable("id") int id, Model model) {
    PostActivity jobDetails = postActivityService.findById(id);
    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", usersService.getCurrentUserProfile());
    return "job-details";
  }

  @PostMapping("dashboard/edit/{id}")
  public String editJob(@PathVariable("id") int id, Model model) {
    PostActivity jobPostActivity = postActivityService.findById(id);
    model.addAttribute("jobPostActivity", jobPostActivity);
    model.addAttribute("user", usersService.getCurrentUserProfile());
    return "add-jobs";
  }
}
