package com.example.project.dtos;

import static java.time.LocalDate.now;
import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Data Transfer Object for search criteria.
 */
@Data
public class SearchCriteriaDto {
  private String job;
  private String location;
  private String partTime;
  private String fullTime;
  private String freelance;
  private String remoteOnly;
  private String officeOnly;
  private String partialRemote;
  private boolean today;
  private boolean days7;
  private boolean days30;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate searchDate;

  /**
   * Builds a SearchCriteriaDto with the specified parameters.
   *
   * @param job The job title or keyword.
   * @param location The job location.
   * @param partTime Part-time job preference.
   * @param fullTime Full-time job preference.
   * @param freelance Freelance job preference.
   * @param remoteOnly Remote-only job preference.
   * @param officeOnly Office-only job preference.
   * @param partialRemote Partial-remote job preference.
   * @param today If jobs posted today should be included.
   * @param days7 If jobs posted in the last 7 days should be included.
   * @param days30 If jobs posted in the last 30 days should be included.
   * @return The built SearchCriteriaDto.
   */
  public static SearchCriteriaDto buildSearchCriteria(
      String job, String location, String partTime,
      String fullTime, String freelance, String remoteOnly,
      String officeOnly, String partialRemote, boolean today,
      boolean days7, boolean days30
  ) {
    SearchCriteriaDto criteria = new SearchCriteriaDto();
    criteria.setJob(job);
    criteria.setLocation(location);
    criteria.setPartTime(partTime);
    criteria.setFullTime(fullTime);
    criteria.setFreelance(freelance);
    criteria.setRemoteOnly(remoteOnly);
    criteria.setOfficeOnly(officeOnly);
    criteria.setPartialRemote(partialRemote);
    criteria.setToday(today);
    criteria.setDays7(days7);
    criteria.setDays30(days30);
    criteria.setSearchDate(
        days30 ? now().minusDays(30) :
          days7 ? now().minusDays(7) :
            today ? now() : null
    );

    if (!criteria.isRemoteSearchEnabled()) {
      criteria.enableRemoteSearch(criteria);
    }
    if (!criteria.isTypeSearchEnabled()) {
      criteria.enableTypeSearch(criteria);
    }

    return criteria;
  }

  public boolean isDateSearchEnabled() {
    return today && days7 && days30;
  }

  public boolean isRemoteSearchEnabled() {
    return officeOnly != null && remoteOnly != null && partialRemote != null;
  }

  public boolean isTypeSearchEnabled() {
    return partTime != null && fullTime != null && freelance != null;
  }

  /**
   * Checks if the given criteria represents a default search.
   *
   * @param criteriaDto The criteria to check.
   * @return true if the criteria represents a default search, false otherwise.
   */
  public boolean isDefaultSearch(SearchCriteriaDto criteriaDto) {
    return !criteriaDto.isDateSearchEnabled()
      && criteriaDto.isRemoteSearchEnabled()
      && criteriaDto.isTypeSearchEnabled()
      && !hasText(job)
      && !hasText(location);
  }

  /**
   * Enables remote search options if not already enabled.
   *
   * @param criteriaDto The criteria to modify.
   */
  public void enableRemoteSearch(SearchCriteriaDto criteriaDto) {
    if (criteriaDto.getOfficeOnly() == null
        && criteriaDto.getRemoteOnly() == null
        && criteriaDto.getPartialRemote() == null
    ) {
      criteriaDto.setOfficeOnly("Office-Only");
      criteriaDto.setRemoteOnly("Remote-Only");
      criteriaDto.setPartialRemote("Partial-Remote");
    }
  }

  /**
   * Enables type search options if not already enabled.
   *
   * @param criteriaDto The criteria to modify.
   */
  public void enableTypeSearch(SearchCriteriaDto criteriaDto) {
    if (criteriaDto.getPartTime() == null
        && criteriaDto.getFullTime() == null
        && criteriaDto.getFreelance() == null
    ) {
      criteriaDto.setPartTime("Part-Time");
      criteriaDto.setFullTime("Full-Time");
      criteriaDto.setFreelance("Freelance");
    }
  }
}
