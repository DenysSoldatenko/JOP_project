package com.example.project.services.impl;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.entities.User;
import com.example.project.repositories.SavedJobRepository;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobPostService;
import com.example.project.services.JobSeekerService;
import com.example.project.services.SavedJobService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link SavedJobService} interface.
 * This service provides methods for managing {@link SavedJob} entities.
 */
@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

  private final JobPostService jobPostService;
  private final JobSeekerService jobSeekerService;
  private final SecurityContextHelper securityContextHelper;
  private final SavedJobRepository savedJobRepository;

  @Override
  public List<SavedJob> findAllByJobSeeker(JobSeeker jobSeeker) {
    return savedJobRepository.findByJobSeeker(jobSeeker);
  }

  @Override
  public void createSavedJob(int jobPostId, SavedJob savedJob) {
    User user = securityContextHelper.getCurrentUser();
    JobSeeker jobSeeker = jobSeekerService.findById(user.getId());
    JobPost jobPost = jobPostService.findById(jobPostId);

    if (jobSeeker != null && jobPost != null) {
      savedJob.setJobSeeker(jobSeeker);
      savedJob.setJobPost(jobPost);
    }

    savedJobRepository.save(savedJob);
  }

  @Override
  public boolean existsByJobPostId(Integer jobPostId, Integer jobSeekId) {
    return savedJobRepository.existsSavedJobByJobPostIdAndJobSeekerId(jobPostId, jobSeekId);
  }

  @Override
  public List<JobPost> findSavedJobPostsByJobSeeker(JobSeeker jobSeeker) {
    return savedJobRepository.findByJobSeeker(jobSeeker)
      .stream()
      .map(SavedJob::getJobPost)
      .toList();
  }
}