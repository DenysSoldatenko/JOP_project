package com.example.project.dtos;

import lombok.Data;

@Data
public class RecruiterJobDto {

  private Long totalCandidates;
  private int jobPostId;
  private String jobTitle;
  private int locationId;
  private String city;
  private String state;
  private String country;
  private int companyId;
  private String companyName;

  public RecruiterJobDto(Long totalCandidates, Integer jobPostId, String jobTitle,
                         Integer locationId, String city, String state,
                         String country, Integer companyId, String companyName) {
    this.totalCandidates = totalCandidates;
    this.jobPostId = jobPostId;
    this.jobTitle = jobTitle;
    this.locationId = locationId;
    this.city = city;
    this.state = state;
    this.country = country;
    this.companyId = companyId;
    this.companyName = companyName;
  }
}
