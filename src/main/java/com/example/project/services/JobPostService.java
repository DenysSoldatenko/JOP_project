package com.example.project.services;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.dtos.SearchCriteriaDto;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.SavedJob;
import java.util.List;

/**
 * Service interface for managing {@link JobPost} entities.
 */
public interface JobPostService {

  void createPostActivity(JobPost postActivity);

  List<RecruiterJobDto> getRecruiterJobs(int recruiter);

  JobPost findById(int id);

  List<JobPost> findAllPostActivities();

  List<JobPost> searchJobs(SearchCriteriaDto searchCriteriaDto);

  void updateJobActivityFlagsForCandidates(List<JobPost> postActivities,
                                           List<JobApplication> jobApplications,
                                           List<SavedJob> savedJobs);
}
