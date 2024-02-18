package com.example.project.services;

import com.example.project.entities.JobSeeker;

/**
 * Service interface for managing {@link JobSeeker} entities.
 */
public interface JobSeekerService {
  JobSeeker findById(Integer id);
}
