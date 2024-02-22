package com.example.project.controllers;

import static com.example.project.dtos.SearchCriteriaDto.buildSearchCriteria;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.dtos.SearchCriteriaDto;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobApplicationService;
import com.example.project.services.JobPostService;
import com.example.project.services.SavedJobService;
import com.example.project.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing job post activity related endpoints.
 */
@Controller
@RequiredArgsConstructor
public class PostActivityController {

  private final UserService userService;
  private final JobPostService jobPostService;
  private final SavedJobService savedJobService;
  private final JobApplicationService jobApplicationService;
  private final SecurityContextHelper securityContextHelper;

  /**
   * Handles the job search functionality and displays the dashboard.
   *
   * @param model The model to add attributes to.
   * @param job The job title or keyword to search for.
   * @param location The location to search for jobs.
   * @param partTime Part-time job preference.
   * @param fullTime Full-time job preference.
   * @param freelance Freelance job preference.
   * @param remoteOnly Remote-only job preference.
   * @param officeOnly Office-only job preference.
   * @param partialRemote Partial-remote job preference.
   * @param today If jobs posted today should be included.
   * @param days7 If jobs posted in the last 7 days should be included.
   * @param days30 If jobs posted in the last 30 days should be included.
   * @return The view name for the dashboard page.
   */
  @GetMapping("/dashboard")
  public String searchJobs(Model model,
      @RequestParam(value = "job", required = false) String job,
      @RequestParam(value = "location", required = false) String location,
      @RequestParam(value = "partTime", required = false) String partTime,
      @RequestParam(value = "fullTime", required = false) String fullTime,
      @RequestParam(value = "freelance", required = false) String freelance,
      @RequestParam(value = "remoteOnly", required = false) String remoteOnly,
      @RequestParam(value = "officeOnly", required = false) String officeOnly,
      @RequestParam(value = "partialRemote", required = false) String partialRemote,
      @RequestParam(value = "today", required = false) boolean today,
      @RequestParam(value = "days7", required = false) boolean days7,
      @RequestParam(value = "days30", required = false) boolean days30
  ) {

    SearchCriteriaDto criteria = buildSearchCriteria(
        job, location, partTime, fullTime, freelance, remoteOnly,
        officeOnly, partialRemote, today, days7, days30
    );

    model.addAttribute("criteria", criteria);
    model.addAttribute("job", criteria.getJob());
    model.addAttribute("location", criteria.getLocation());

    List<JobPost> jobPost = criteria.isDefaultSearch(criteria)
        ? jobPostService.findAllPostActivities()
        : jobPostService.searchJobs(criteria);

    Object userProfile = userService.getCurrentUserProfile();
    model.addAttribute("user", userProfile);
    String currentUsername = securityContextHelper.getCurrentUser().getEmail();
    model.addAttribute("username", currentUsername);

    if (securityContextHelper.isCurrentUserRecruiter()) {
      int recruiterId = securityContextHelper.getCurrentUser().getId();
      List<RecruiterJobDto> recruiterJobs = jobPostService.getRecruiterJobs(recruiterId);
      model.addAttribute("jobPost", recruiterJobs);
    } else {
      List<JobApplication> applications = jobApplicationService.findAllByJobSeeker((JobSeeker) userProfile);
      List<SavedJob> savedJobs = savedJobService.findAllByJobSeeker((JobSeeker) userProfile);
      jobPostService.updateJobActivityFlagsForCandidates(jobPost, applications, savedJobs);
      model.addAttribute("jobPost", jobPost);
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
    model.addAttribute("jobPostActivity", new JobPost());
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
  public String addJobPost(JobPost jobPostActivity, Model model) {
    jobPostService.createPostActivity(jobPostActivity);
    model.addAttribute("jobPostActivity", jobPostActivity);
    return "redirect:/dashboard";
  }
}