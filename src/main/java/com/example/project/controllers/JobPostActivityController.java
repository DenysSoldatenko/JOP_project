package com.example.project.controllers;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.entities.PostActivity;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.PostActivityService;
import com.example.project.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class for managing job post activity related endpoints.
 */
@Controller
@RequiredArgsConstructor
public class JobPostActivityController {

  private final UserService userService;
  private final PostActivityService postActivityService;
  private final SecurityContextHelper securityContextHelper;

  @GetMapping("/dashboard")
  public String searchJobs(Model model) {
    Object currentUserProfile = userService.getCurrentUserProfile();
    String currentUsername = securityContextHelper.getCurrentUser().getEmail();
    model.addAttribute("username", currentUsername);
    model.addAttribute("user", currentUserProfile);

    if (securityContextHelper.isCurrentUserRecruiter()) {
      int recruiterId = securityContextHelper.getCurrentUser().getId();
      List<RecruiterJobDto> recruiterJobs = postActivityService.getRecruiterJobs(recruiterId);
      model.addAttribute("jobPost", recruiterJobs);
    }

    return "dashboard";
  }

  /**
   * Displays the form for adding a new job post activity.
   *
   * @param model The model to be populated with necessary attributes.
   * @return The view name "add-jobs" to render the form.
   */
  @GetMapping("/dashboard/add")
  public String showJobPostForm(Model model) {
    model.addAttribute("jobPostActivity", new PostActivity());
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "add-jobs";
  }

  /**
   * Handles the submission of a new job post activity.
   *
   * @param jobPostActivity The job post activity object to be added.
   * @param model           The model to be populated with necessary attributes.
   * @return Redirects to the dashboard page after adding the job post activity.
   */
  @PostMapping("/dashboard/addNew")
  public String addJobPost(PostActivity jobPostActivity, Model model) {
    postActivityService.createPostActivity(jobPostActivity);
    model.addAttribute("jobPostActivity", jobPostActivity);
    return "redirect:/dashboard";
  }
}