package com.example.project.controllers;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobPostService;
import com.example.project.services.JobApplicationService;
import com.example.project.services.SavedJobService;
import com.example.project.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static org.springframework.util.StringUtils.hasText;

/**
 * Controller class for managing job post activity related endpoints.
 */
@Controller
@RequiredArgsConstructor
public class PostActivityController {

  private final UserService userService;
  private final JobApplicationService jobApplicationService;
  private final SavedJobService savedJobService;
  private final JobPostService jobPostService;
  private final SecurityContextHelper securityContextHelper;

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
    model.addAttribute("partTime", Objects.equals(partTime, "Part-Time"));
    model.addAttribute("fullTime", Objects.equals(partTime, "Full-Time"));
    model.addAttribute("freelance", Objects.equals(partTime, "Freelance"));

    model.addAttribute("remoteOnly", Objects.equals(partTime, "Remote-Only"));
    model.addAttribute("officeOnly", Objects.equals(partTime, "Office-Only"));
    model.addAttribute("partialRemote", Objects.equals(partTime, "Partial-Remote"));

    model.addAttribute("today", today);
    model.addAttribute("days7", days7);
    model.addAttribute("days30", days30);

    model.addAttribute("job", job);
    model.addAttribute("location", location);

    LocalDate searchDate = null;
    List<JobPost> jobPost = null;
    boolean dateSearchFlag = true;
    boolean remote = true;
    boolean type = true;

    if (days30) {
      searchDate = now().minusDays(30);
    } else if (days7) {
      searchDate = now().minusDays(7);
    } else if (today) {
      searchDate = now();
    } else {
      dateSearchFlag = false;
    }

    if (partTime == null && fullTime == null && freelance == null) {
      partTime = "Part-Time";
      fullTime = "Full-Time";
      freelance = "Freelance";
      remote = false;
    }

    if (officeOnly == null && remoteOnly == null && partialRemote == null) {
      officeOnly = "Office-Only";
      remoteOnly = "Remote-Only";
      partialRemote = "Partial-Remote";
      type = false;
    }

    if (!dateSearchFlag && !remote && !type && !hasText(job) && !hasText(location)) {
      jobPost = jobPostService.findAllPostActivities();
    } else {
      jobPost = jobPostService.searchJobs(job, location, asList(partTime, fullTime, freelance),
        asList(remoteOnly, officeOnly, partialRemote), searchDate);
    }

    Object currentUserProfile = userService.getCurrentUserProfile();
    String currentUsername = securityContextHelper.getCurrentUser().getEmail();
    model.addAttribute("username", currentUsername);
    model.addAttribute("user", currentUserProfile);

    if (securityContextHelper.isCurrentUserRecruiter()) {
      int recruiterId = securityContextHelper.getCurrentUser().getId();
      List<RecruiterJobDto> recruiterJobs = jobPostService.getRecruiterJobs(recruiterId);
      System.out.println(recruiterJobs.toString());
      model.addAttribute("jobPost", recruiterJobs);
    } else {
      List<JobApplication> jobApplications = jobApplicationService.findAllByJobSeeker((JobSeeker) currentUserProfile);
      List<SavedJob> savedJobs = savedJobService.findAllByJobSeeker((JobSeeker) currentUserProfile);
      jobPostService.updateJobActivityFlagsForCandidates(jobPost, jobApplications, savedJobs);
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