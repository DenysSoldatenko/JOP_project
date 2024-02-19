package com.example.project.services;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.User;

/**
 * Service interface for managing {@link JobSeeker} entities.
 */
public interface JobSeekerService {
  JobSeeker findById(Integer id);

  void createJobSeeker(User user, JobSeeker jobSeeker);
}
