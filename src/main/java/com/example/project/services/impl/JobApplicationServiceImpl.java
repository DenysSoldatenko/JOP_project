package com.example.project.services.impl;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.repositories.JobApplicationRepository;
import com.example.project.services.JobApplicationService;
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

  private final JobApplicationRepository jobApplicationRepository;

  @Override
  public List<JobApplication> findAllByJobSeeker(JobSeeker jobSeeker) {
    return jobApplicationRepository.findByJobSeeker(jobSeeker);
  }

  @Override
  public List<JobApplication> findAllByJobPost(JobPost jobPost) {
    return List.of();
  }
}
