package com.example.project.controllers;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.services.SavedJobService;
import com.example.project.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for managing saved jobs functionality.
 */
@Controller
@RequiredArgsConstructor
public class SavedJobController {

  private final UserService userService;
  private final SavedJobService savedJobService;

  /**
   * Saves a job post to the user's saved jobs list.
   *
   * @param jobPostId The ID of the job post to save.
   * @param savedJob  The {@link SavedJob} object containing the job post details.
   * @return Redirects to the dashboard page.
   */
  @PostMapping("/job-details/save/{id}")
  public String save(@PathVariable("id") int jobPostId, SavedJob savedJob) {
    savedJobService.createSavedJob(jobPostId, savedJob);
    return "redirect:/dashboard";
  }

  /**
   * Displays the saved jobs for the current user.
   *
   * @param model The {@link Model} object to add attributes for the view.
   * @return The name of the view to render, which is "saved-jobs".
   */
  @GetMapping("/saved-jobs")
  public String savedJobs(Model model) {
    Object userProfile = userService.getCurrentUserProfile();
    List<JobPost> jobPost = savedJobService.findSavedJobPostsByJobSeeker((JobSeeker) userProfile);
    model.addAttribute("jobPost", jobPost);
    model.addAttribute("user", userProfile);
    return "saved-jobs";
  }
}