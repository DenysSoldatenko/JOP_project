package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.JOB_NOT_FOUND;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.dtos.RecruiterJobSummaryDto;
import com.example.project.entities.Company;
import com.example.project.entities.Location;
import com.example.project.entities.PostActivity;
import com.example.project.entities.User;
import com.example.project.exceptions.JobNotFoundException;
import com.example.project.repositories.PostActivityRepository;
import com.example.project.security.SecurityContextHelper;
import com.example.project.services.PostActivityService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link PostActivityService} interface.
 * Provides methods to manage post activities.
 */
@Service
@RequiredArgsConstructor
public class PostActivityServiceImpl implements PostActivityService {

  private final PostActivityRepository postActivityRepository;
  private final SecurityContextHelper securityContextHelper;

  @Override
  public void createPostActivity(PostActivity postActivity) {
    User user = securityContextHelper.getCurrentUser();
    postActivity.setUser(user);
    postActivity.setPostedDate(new Date());
    postActivityRepository.save(postActivity);
  }

  @Override
  public List<RecruiterJobDto> getRecruiterJobs(int id) {
    List<RecruiterJobSummaryDto> summaryDtoList = postActivityRepository.getRecruiterJobs(id);
    List<RecruiterJobDto> jobDtoList = new ArrayList<>();

    for (RecruiterJobSummaryDto summaryDto : summaryDtoList) {
      Location location = new Location(summaryDto.getLocationId(), summaryDto.getCity(),
          summaryDto.getState(), summaryDto.getCountry());
      Company company = new Company(summaryDto.getCompanyId(), summaryDto.getCompanyName(), "");
      jobDtoList.add(new RecruiterJobDto(summaryDto.getTotalCandidates(), summaryDto.getJobPostId(),
          summaryDto.getJobTitle(), location, company));
    }

    return jobDtoList;
  }

  @Override
  public PostActivity findById(int id) {
    return postActivityRepository.findById(id)
      .orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND + id));
  }
}
