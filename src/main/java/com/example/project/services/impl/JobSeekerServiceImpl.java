package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.JOB_SEEKER_NOT_FOUND;
import static com.example.project.utils.FileStorageHelper.storeJobSeekerPhoto;
import static java.util.Collections.singletonList;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.Skill;
import com.example.project.entities.User;
import com.example.project.exceptions.JobSeekerNotFoundException;
import com.example.project.repositories.JobSeekerRepository;
import com.example.project.services.JobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

  @Override
  public void createJobSeeker(User user, JobSeeker jobSeeker,
                              MultipartFile photo, MultipartFile pdf) {
    storeJobSeekerPhoto(user, jobSeeker, photo, pdf);
    JobSeeker existingJobSeeker = jobSeekerRepository.findWithSkillsById(user.getId())
        .orElseThrow(() -> new JobSeekerNotFoundException(JOB_SEEKER_NOT_FOUND + user.getId()));

    existingJobSeeker.setFirstName(jobSeeker.getFirstName());
    existingJobSeeker.setLastName(jobSeeker.getLastName());
    existingJobSeeker.setCity(jobSeeker.getCity());
    existingJobSeeker.setState(jobSeeker.getState());
    existingJobSeeker.setCountry(jobSeeker.getCountry());
    existingJobSeeker.setWorkAuthorization(jobSeeker.getWorkAuthorization());
    existingJobSeeker.setEmploymentType(jobSeeker.getEmploymentType());
    existingJobSeeker.setResume(jobSeeker.getResume());
    existingJobSeeker.setProfilePhoto(jobSeeker.getProfilePhoto());
    existingJobSeeker.setSkills(jobSeeker.getSkills());

    jobSeekerRepository.save(existingJobSeeker);
  }
}
