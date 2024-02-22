package com.example.project.services;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import java.util.List;

/**
 * Service interface for managing {@link JobApplication} entities.
 */
public interface JobApplicationService {

  List<JobApplication> findAllByJobSeeker(JobSeeker seeker);

  List<JobApplication> findAllByJobPost(JobPost jobPost);
}
