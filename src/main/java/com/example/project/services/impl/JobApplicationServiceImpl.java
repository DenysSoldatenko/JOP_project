package com.example.project.services.impl;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.User;
import com.example.project.repositories.JobApplicationRepository;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobApplicationService;
import com.example.project.services.JobPostService;
import com.example.project.services.JobSeekerService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link JobApplicationService} interface.
 * This service provides methods for managing {@link JobApplication} entities.
 */
@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

  private final JobPostService jobPostService;
  private final JobSeekerService jobSeekerService;
  private final SecurityContextHelper securityContextHelper;
  private final JobApplicationRepository jobApplicationRepository;

  @Override
  public List<JobApplication> findAllByJobSeeker(JobSeeker jobSeeker) {
    return jobApplicationRepository.findByJobSeeker(jobSeeker);
  }

  @Override
  public List<JobApplication> findAllByJobPost(JobPost jobPost) {
    return jobApplicationRepository.findByJobPost(jobPost);
  }

  @Override
  public void createJobApplication(int jobPostId, JobApplication jobApplication) {
    User user = securityContextHelper.getCurrentUser();
    JobSeeker jobSeeker = jobSeekerService.findById(user.getId());
    JobPost jobPost = jobPostService.findById(jobPostId);

    if (jobSeeker != null && jobPost != null) {
      jobApplication.setJobSeeker(jobSeeker);
      jobApplication.setJobPost(jobPost);
      jobApplication.setApplyDate(new Date());
    }

    jobApplicationRepository.save(jobApplication);
  }
}
