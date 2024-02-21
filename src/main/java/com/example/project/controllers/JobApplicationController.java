package com.example.project.controllers;

import com.example.project.entities.JobPost;
import com.example.project.services.JobPostService;
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
public class JobApplicationController {

  private final JobPostService jobPostService;
  private final UserService usersService;

  @GetMapping("job-details-apply/{id}")
  public String showJobDetails(@PathVariable("id") int id, Model model) {
    JobPost jobDetails = jobPostService.findById(id);
    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", usersService.getCurrentUserProfile());
    return "job-details";
  }

  @PostMapping("dashboard/edit/{id}")
  public String editJob(@PathVariable("id") int id, Model model) {
    JobPost jobPostActivity = jobPostService.findById(id);
    model.addAttribute("jobPostActivity", jobPostActivity);
    model.addAttribute("user", usersService.getCurrentUserProfile());
    return "add-jobs";
  }
}
