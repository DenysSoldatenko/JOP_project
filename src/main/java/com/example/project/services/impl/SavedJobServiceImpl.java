package com.example.project.services.impl;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import com.example.project.repositories.SavedJobRepository;
import com.example.project.services.SavedJobService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements SavedJobService {

  private final SavedJobRepository savedJobRepository;

  @Override
  public List<SavedJob> findAllByJobSeeker(JobSeeker jobSeeker) {
    return savedJobRepository.findByJobSeeker(jobSeeker);
  }

  @Override
  public List<SavedJob> findAllByJobPost(JobPost jobPost) {
    return savedJobRepository.findByJobPost(jobPost);
  }
}