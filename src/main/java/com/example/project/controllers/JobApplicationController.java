package com.example.project.controllers;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.services.JobApplicationService;
import com.example.project.services.JobPostService;
import com.example.project.services.UserService;
import com.example.project.utils.JobProfileProcessor;
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

  private final UserService userService;
  private final JobPostService jobPostService;
  private final JobApplicationService jobApplicationService;
  private final JobProfileProcessor jobProfileProcessor;

  /**
   * Displays job details for a job seeker to apply.
   *
   * @param id    The ID of the job post activity.
   * @param model The model to be populated with necessary attributes.
   * @return The name of the view to render, which is "job-details".
   */
  @GetMapping("job-details-apply/{id}")
  public String showJobDetails(@PathVariable("id") int id, Model model) {
    JobPost jobDetails = jobPostService.findById(id);
    model.addAttribute("applyJob", new JobApplication());
    model.addAttribute("jobDetails", jobDetails);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return jobProfileProcessor.processJobPostDetails(model, jobDetails);
  }

  /**
   * Handles the submission of a job application by a job seeker.
   *
   * @param id              The ID of the job post activity.
   * @param jobApplication The job application object to be created.
   * @return Redirects to the dashboard page after applying for the job.
   */
  @PostMapping("job-details/apply/{id}")
  public String apply(@PathVariable("id") int id, JobApplication jobApplication) {
    jobApplicationService.createJobApplication(id, jobApplication);
    return "redirect:/dashboard";
  }
}
