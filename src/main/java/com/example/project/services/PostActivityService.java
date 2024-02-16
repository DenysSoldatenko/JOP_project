package com.example.project.services;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.entities.PostActivity;
import java.util.List;

/**
 * Service interface for managing {@link PostActivity} entities.
 */
public interface PostActivityService {

  void createPostActivity(PostActivity postActivity);

  List<RecruiterJobDto> getRecruiterJobs(int recruiter);

  PostActivity findById(int id);
}
