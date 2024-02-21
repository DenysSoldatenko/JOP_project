package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.JOB_NOT_FOUND;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static java.util.stream.Collectors.toList;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.entities.Company;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.Location;
import com.example.project.entities.SavedJob;
import com.example.project.entities.User;
import com.example.project.exceptions.JobNotFoundException;
import com.example.project.repositories.JobPostRepository;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.JobPostService;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link JobPostService} interface.
 * Provides methods to manage post activities.
 */
@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

  private final JobPostRepository jobPostRepository;
  private final SecurityContextHelper securityContextHelper;

  @Override
  public void createPostActivity(JobPost postActivity) {
    User user = securityContextHelper.getCurrentUser();
    postActivity.setUser(user);
    postActivity.setPostedDate(new Date());
    jobPostRepository.save(postActivity);
  }

  @Override
  public List<RecruiterJobDto> getRecruiterJobs(int id) {
    return jobPostRepository.getRecruiterJobs(id).stream()
      .map(
        summaryDto -> new RecruiterJobDto(
            summaryDto.getTotalCandidates(),
            summaryDto.getJobPostId(),
            summaryDto.getJobTitle(),
            new Location(summaryDto.getLocationId(), summaryDto.getCity(),
              summaryDto.getState(), summaryDto.getCountry()),
            new Company(summaryDto.getCompanyId(), summaryDto.getCompanyName(), "")
        )
      ).collect(toList());
  }

  @Override
  public JobPost findById(int id) {
    return jobPostRepository.findById(id)
      .orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND + id));
  }

  @Override
  public List<JobPost> findAllPostActivities() {
    return jobPostRepository.findAll();
  }


  @Override
  public List<JobPost> searchJobs(String job, String location, List<String> type,
                                  List<String> remote, LocalDate searchDate) {
    if (searchDate == null) {
      return jobPostRepository.findJobsWithoutDate(job, location, remote, type);
    } else {
      Date date = from(searchDate.atStartOfDay(systemDefault()).toInstant());
      return jobPostRepository.findJobsWithDate(job, location, remote, type, date);
    }
  }

  @Override
  public void updateJobActivityFlagsForCandidates(List<JobPost> postActivities,
                                                  List<JobApplication> jobApplications,
                                                  List<SavedJob> savedJobs) {
    for (JobPost jobActivity : postActivities) {
      boolean exist = jobApplications.stream()
          .anyMatch(jobApplication ->
            Objects.equals(jobActivity.getJobPostId(), jobApplication.getJobPost().getJobPostId())
          );

      boolean saved = savedJobs.stream()
          .anyMatch(savedJob ->
            Objects.equals(jobActivity.getJobPostId(), savedJob.getJobPost().getJobPostId())
          );

      jobActivity.setIsActive(exist);
      jobActivity.setIsSaved(saved);
    }
  }
}
