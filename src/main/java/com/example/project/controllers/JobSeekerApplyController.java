package com.example.project.controllers;

import com.example.project.entities.PostActivity;
import com.example.project.services.PostActivityService;
import com.example.project.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {

  private final PostActivityService postActivityService;
  private final UserService usersService;

  @GetMapping("job-details-apply/{id}")
  public String display(@PathVariable("id") int id, Model model) {
    PostActivity jobDetails = postActivityService.findById(id);
    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", usersService.getCurrentUserProfile());
    return "job-details";
  }
}