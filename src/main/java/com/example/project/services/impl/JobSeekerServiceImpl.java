package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.JOB_SEEKER_NOT_FOUND;
import static java.util.Collections.singletonList;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.Skill;
import com.example.project.exceptions.JobSeekerNotFoundException;
import com.example.project.repositories.JobSeekerRepository;
import com.example.project.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link JobSeekerService} interface.
 * This service provides methods for managing {@link JobSeeker} entities.
 */
@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {
  private final JobSeekerRepository jobSeekerRepository;

  @Override
  public JobSeeker findById(Integer id) {
    JobSeeker jobSeeker = jobSeekerRepository.findWithSkillsById(id)
        .orElseThrow(() -> new JobSeekerNotFoundException(JOB_SEEKER_NOT_FOUND + id));
    jobSeeker.setSkills(jobSeeker.getSkills().isEmpty()
        ? singletonList(new Skill()) : jobSeeker.getSkills());
    return jobSeeker;
  }
}
