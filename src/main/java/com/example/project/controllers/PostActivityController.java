package com.example.project.controllers;

import static com.example.project.dtos.SearchCriteriaDto.buildSearchCriteria;

import com.example.project.dtos.SearchCriteriaDto;
import com.example.project.entities.JobPost;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobPostService;
import com.example.project.services.UserService;
import com.example.project.utils.JobProfileProcessor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for managing job post activity related endpoints.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class PostActivityController {

  private final UserService userService;
  private final JobPostService jobPostService;
  private final JobProfileProcessor jobProfileProcessor;
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
  @GetMapping
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

    Object userProfile = userService.getCurrentUserProfile();
    model.addAttribute("user", userProfile);

    String currentUsername = securityContextHelper.getCurrentUser().getEmail();
    model.addAttribute("username", currentUsername);

    return jobProfileProcessor.processJobPostDetails(model, userProfile, criteria);
  }

  /**
   * Handles the global search for job posts based on various criteria.
   *
   * @param model The model to be populated with search results.
   * @param job The job title or keyword to search for.
   * @param location The location or city to filter job posts by.
   * @param partTime Indicates if part-time jobs should be included in the search.
   * @param fullTime Indicates if full-time jobs should be included in the search.
   * @param freelance Indicates if freelance jobs should be included in the search.
   * @param remoteOnly Indicates if only remote jobs should be included in the search.
   * @param officeOnly Indicates if only office-based jobs should be included in the search.
   * @param partialRemote Indicates if partial remote jobs should be included in the search.
   * @param today Indicates if job posts posted today should be included in the search.
   * @param days7 Indicates if job posts posted within the last 7 days should be included in the search.
   * @param days30 Indicates if job posts posted within the last 30 days should be included in the search.
   * @return The name of the view template to render, "global-search".
   */
  @GetMapping("/global-search")
  public String globalSearch(Model model,
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

    List<JobPost> jobPost = jobProfileProcessor.getJobPosts(criteria);
    model.addAttribute("jobPost", jobPost);
    return "global-search";
  }

  /**
   * Displays the form for adding a new job post activity.
   *
   * @param model The model to be populated with necessary attributes.
   * @return The view name "add-jobs" to render the form.
   */
  @GetMapping("/add")
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
  @PostMapping("/addNew")
  public String addJobPost(JobPost jobPostActivity, Model model) {
    jobPostService.createPostActivity(jobPostActivity);
    model.addAttribute("jobPostActivity", jobPostActivity);
    return "redirect:/dashboard";
  }

  /**
   * Edits a job post activity.
   *
   * @param id    The ID of the job post activity to edit.
   * @param model The model to be populated with necessary attributes.
   * @return The name of the view to render, which is "add-jobs".
   */
  @PostMapping("/edit/{id}")
  public String editJobPost(@PathVariable("id") int id, Model model) {
    JobPost jobPostActivity = jobPostService.findById(id);
    model.addAttribute("jobPostActivity", jobPostActivity);
    model.addAttribute("user", userService.getCurrentUserProfile());
    return "add-jobs";
  }
}