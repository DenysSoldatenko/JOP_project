package com.example.project.services;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Service interface for managing {@link SavedJob} entities.
 */
public interface SavedJobService {

  List<SavedJob> findAllByJobSeeker(JobSeeker jobSeeker);

  void createSavedJob(int jobPostId, SavedJob savedJob);

  boolean existsByJobPostId(@Param("jobPostId") Integer jobPostId,
                            @Param("jobSeekId") Integer jobSeekId);

  List<JobPost> findSavedJobPostsByJobSeeker(JobSeeker jobSeeker);
}