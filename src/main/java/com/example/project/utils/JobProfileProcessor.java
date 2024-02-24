package com.example.project.utils;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.dtos.SearchCriteriaDto;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobApplicationService;
import com.example.project.services.JobPostService;
import com.example.project.services.JobSeekerService;
import com.example.project.services.SavedJobService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Utility component for processing job profile details based on user context.
 */
@Component
@RequiredArgsConstructor
public class JobProfileProcessor {

  private final JobPostService jobPostService;
  private final SavedJobService savedJobService;
  private final JobSeekerService jobSeekerService;
  private final JobApplicationService jobApplicationService;
  private final SecurityContextHelper securityContextHelper;

  /**
   * Processes job post details based on the search criteria.
   *
   * @param model       The {@link Model} object to add attributes for the view.
   * @param userProfile The user profile object, which can be a {@link JobSeeker} or a recruiter.
   * @param criteria    The {@link SearchCriteriaDto} object containing search criteria.
   * @return The name of the view to render, which is "dashboard".
   */
  public String processJobPostDetails(Model model, Object userProfile, SearchCriteriaDto criteria) {
    List<JobPost> jobPost = criteria.isDefaultSearch(criteria)
        ? jobPostService.findAllPostActivities()
        : jobPostService.searchJobs(criteria);

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
   * Processes job post details for display in the view.
   *
   * @param model The {@link Model} object to add attributes for the view.
   * @param jobDetails The {@link JobPost} object representing the job details to display.
   * @return The name of the view to render, which is "job-details".
   */
  public String processJobPostDetails(Model model, JobPost jobDetails) {
    List<JobApplication> jobApplications = jobApplicationService.findAllByJobPost(jobDetails);

    if (securityContextHelper.isCurrentUserRecruiter()) {
      model.addAttribute("applyList", jobApplications);
    } else {
      JobSeeker jobSeeker = jobSeekerService.findById(securityContextHelper.getCurrentUser().getId());
      boolean alreadyApplied = jobPostService.existsByJobPostId(jobDetails.getJobPostId(), jobSeeker.getJobSeekerId());
      boolean alreadySaved = savedJobService.existsByJobPostId(jobDetails.getJobPostId(), jobSeeker.getJobSeekerId());
      model.addAttribute("alreadyApplied", alreadyApplied);
      model.addAttribute("alreadySaved", alreadySaved);
    }

    return "job-details";
  }
}
