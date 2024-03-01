package com.example.project.initializers;

import static java.time.ZonedDateTime.now;
import static java.util.Date.from;
import static java.util.stream.IntStream.range;

import com.example.project.entities.Company;
import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.Location;
import com.example.project.entities.Recruiter;
import com.example.project.entities.SavedJob;
import com.example.project.repositories.CompanyRepository;
import com.example.project.repositories.JobApplicationRepository;
import com.example.project.repositories.JobPostRepository;
import com.example.project.repositories.JobSeekerRepository;
import com.example.project.repositories.LocationRepository;
import com.example.project.repositories.RecruiterRepository;
import com.example.project.repositories.SavedJobRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

/**
 * Component responsible for seeding job-related data into the database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JobDataInitializer {

  private final Faker faker = new Faker();

  private final JobPostRepository jobPostRepository;
  private final CompanyRepository companyRepository;
  private final LocationRepository locationRepository;
  private final SavedJobRepository savedJobRepository;
  private final JobSeekerRepository jobSeekerRepository;
  private final RecruiterRepository recruiterRepository;
  private final JobApplicationRepository jobApplicationRepository;

  /**
   * Seeds job-related data into the database, including job posts, companies, locations,
   * job applications, and saved jobs.
   */
  public void seedJobData() {
    log.info("Starting job data seeding...");

    List<JobSeeker> jobSeekers = jobSeekerRepository.findAll();
    List<Recruiter> recruiters = recruiterRepository.findAll();

    log.info("Found {} job seekers and {} recruiters.", jobSeekers.size(), recruiters.size());

    List<Company> companies = createCompanies();
    List<Location> locations = createLocations();
    List<JobPost> jobPosts = createJobPosts(recruiters, companies, locations);

    log.info("Created {} companies, {} locations, and {} job posts.", companies.size(), locations.size(), jobPosts.size());

    jobSeekers.forEach(jobSeeker -> {
      log.info("Processing job applications for job seeker: {} {}", jobSeeker.getFirstName(), jobSeeker.getLastName());
      jobPosts.forEach(jobPost -> createJobApplication(jobSeeker, jobPost));
      jobPosts.forEach(jobPost -> createSavedJob(jobSeeker, jobPost));
    });

    log.info("Job data seeding completed.");
  }

  private List<Company> createCompanies() {
    log.info("Creating companies...");
    Set<String> usedCompanyNames = new HashSet<>();
    List<Company> companies = range(0, 10).mapToObj(i -> {
      String companyName;
      do {
        companyName = faker.company().name();
      } while (usedCompanyNames.contains(companyName));
      usedCompanyNames.add(companyName);

      Company company = new Company();
      company.setName(companyName);
      return companyRepository.save(company);
    }).toList();
    log.info("Created {} companies.", companies.size());
    return companies;
  }

  private List<Location> createLocations() {
    log.info("Creating locations...");
    Set<String> usedCountries = new HashSet<>();
    List<Location> locations = range(0, 10).mapToObj(i -> {
      String country;
      do {
        country = faker.address().country();
      } while (usedCountries.contains(country));
      usedCountries.add(country);

      Location location = new Location();
      location.setCity(faker.address().city());
      location.setState(faker.address().state());
      location.setCountry(country);
      return locationRepository.save(location);
    }).toList();
    log.info("Created {} locations.", locations.size());
    return locations;
  }

  private List<JobPost> createJobPosts(List<Recruiter> recruiters, List<Company> companies, List<Location> locations) {
    log.info("Creating job posts...");
    List<JobPost> jobPosts = recruiters.stream().flatMap(recruiter ->
        range(0, 10).mapToObj(i -> {
          JobPost jobPost = new JobPost();
          jobPost.setDescriptionOfJob(faker.lorem().paragraph());
          jobPost.setJobTitle(faker.job().title());
          jobPost.setJobType(faker.options().option("Full-Time", "Part-Time", "Freelance"));
          jobPost.setPostedDate(from(now().minusDays(faker.number().numberBetween(1, 30)).toInstant()));
          jobPost.setRemote(faker.options().option("Remote-Only", "Office-Only", "Partial-Remote"));
          jobPost.setSalary(faker.number().digits(5));
          jobPost.setUser(recruiter.getUser());
          jobPost.setCompany(companies.get(faker.random().nextInt(companies.size())));
          jobPost.setLocation(locations.get(faker.random().nextInt(locations.size())));
          return jobPostRepository.save(jobPost);
        })
    ).toList();
    log.info("Created {} job posts.", jobPosts.size());
    return jobPosts;
  }

  private void createJobApplication(JobSeeker jobSeeker, JobPost jobPost) {
    int randomApplyDecision = faker.number().numberBetween(1, 11);
    if (randomApplyDecision % 3 == 0) {
      JobApplication jobApplication = new JobApplication();
      jobApplication.setApplyDate(from(now().minusDays(faker.number().numberBetween(1, 7)).toInstant()));
      jobApplication.setCoverLetter(faker.lorem().sentence(10));
      jobApplication.setJobPost(jobPost);
      jobApplication.setJobSeeker(jobSeeker);
      jobApplicationRepository.save(jobApplication);
      log.info("Applied for job randomly: job seeker {} {} applied for job post {}", jobSeeker.getFirstName(), jobSeeker.getLastName(), jobPost.getJobPostId());
    }
  }

  private void createSavedJob(JobSeeker jobSeeker, JobPost jobPost) {
    int shouldSaveJob = faker.number().numberBetween(1, 11);
    if (shouldSaveJob % 3 == 0) {
      SavedJob savedJob = new SavedJob();
      savedJob.setJobSeeker(jobSeeker);
      savedJob.setJobPost(jobPost);
      savedJobRepository.save(savedJob);
      log.info("Saved job post '{}' for job seeker: {} {}", jobPost.getJobTitle(), jobSeeker.getFirstName(), jobSeeker.getLastName());
    }
  }
}
