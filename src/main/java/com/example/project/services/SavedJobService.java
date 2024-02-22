package com.example.project.services;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import java.util.List;

/**
 * Service interface for managing {@link SavedJob} entities.
 */
public interface SavedJobService {

  List<SavedJob> findAllByJobSeeker(JobSeeker jobSeeker);

  List<SavedJob> findAllByJobPost(JobPost jobPost);
}