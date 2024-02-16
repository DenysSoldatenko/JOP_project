package com.example.project.dtos;

import com.example.project.entities.Company;
import com.example.project.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO for representing recruiter job details.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RecruiterJobDto {

  private Long totalCandidates;
  private Integer jobPostId;
  private String jobTitle;
  private Location location;
  private Company company;
}
