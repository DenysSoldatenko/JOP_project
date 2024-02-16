package com.example.project.dtos;

import lombok.Data;

/**
 * DTO for summarizing recruiter job details.
 */
@Data
public class RecruiterJobSummaryDto {

  private Long totalCandidates;
  private int jobPostId;
  private String jobTitle;
  private int locationId;
  private String city;
  private String state;
  private String country;
  private int companyId;
  private String companyName;

  /**
   * Constructs a new RecruiterJobSummaryDto object with provided details.
   *
   * @param totalCandidates The total number of candidates for the job.
   * @param jobPostId       The ID of the job post.
   * @param jobTitle        The title of the job.
   * @param locationId      The ID of the location.
   * @param city            The city of the job location.
   * @param state           The state of the job location.
   * @param country         The country of the job location.
   * @param companyId       The ID of the company associated with the job.
   * @param companyName     The name of the company associated with the job.
   */
  public RecruiterJobSummaryDto(Long totalCandidates, Integer jobPostId, String jobTitle,
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
