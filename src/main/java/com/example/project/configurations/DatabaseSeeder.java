package com.example.project.configurations;

import static java.time.ZonedDateTime.now;
import static java.util.Date.from;
import static java.util.stream.IntStream.range;

import com.example.project.entities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.project.repositories.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class to seed initial data into the database on application startup.
 * This configuration includes creation of users, job seekers, recruiters, companies,
 * locations, job posts, and their relationships.
 * <p>
 * Note: Ensure that in {@link JobApplication} and {@link SavedJob} entities, the relationship
 * to {@link JobPost} is defined as {@code @ManyToOne} without {@code CascadeType.ALL}
 * to properly insert data during seeding.
 */
@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserTypeRepository userTypeRepository;
  private final JobSeekerRepository jobSeekerRepository;
  private final RecruiterRepository recruiterRepository;
  private final SavedJobRepository savedJobRepository;
  private final JobPostRepository jobPostRepository;
  private final CompanyRepository companyRepository;
  private final LocationRepository locationRepository;
  private final JobApplicationRepository jobSeekerAppliesRepository;

  private final Set<String> usedEmails = new HashSet<>();
  private final Faker faker = new Faker();

  @Bean
  CommandLineRunner initData() {
    return args -> {
      UserType jobSeekerType = createUserType("Job Seeker");
      UserType recruiterType = createUserType("Recruiter");

      List<JobSeeker> jobSeekers = range(0, 10)
        .mapToObj(i -> createJobSeeker(jobSeekerType))
        .toList();

      List<Recruiter> recruiters = range(0, 10)
        .mapToObj(i -> createRecruiter(recruiterType))
        .toList();

      List<Company> companies = createCompanies();
      List<Location> locations = createLocations();

      List<JobPost> postActivities = recruiters.stream()
        .flatMap(recruiter -> range(0, 10)
          .mapToObj(i -> createPostActivity(recruiter, companies, locations)))
        .toList();

      jobSeekers.forEach(jobSeeker -> postActivities.forEach(postActivity ->
        createJobSeekerApply(jobSeeker, postActivity)));

      jobSeekers.forEach(jobSeeker -> postActivities.forEach(postActivity ->
        createSavedJobsForJobSeeker(jobSeeker, postActivity)));
    };
  }

  private UserType createUserType(String typeName) {
    UserType userType = new UserType();
    userType.setUserTypeName(typeName);
    return userTypeRepository.save(userType);
  }

  private List<Company> createCompanies() {
    Set<String> usedCompanyNames = new HashSet<>();
    return range(0, 10).mapToObj(i -> {
      String companyName;
      do {
        companyName = faker.company().name();
      } while (usedCompanyNames.contains(companyName));
      usedCompanyNames.add(companyName);

      Company company = new Company();
      company.setName(companyName);
      return companyRepository.save(company);
    }).toList();
  }

  private List<Location> createLocations() {
    Set<String> usedCountries = new HashSet<>();
    return range(0, 10).mapToObj(i -> {
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
  }

  private List<Skill> createSkillsForJobSeeker(JobSeeker jobSeeker) {
    int numberOfSkills = faker.number().numberBetween(1, 4);
    List<Skill> skills = new ArrayList<>();
    for (int i = 0; i < numberOfSkills; i++) {
      Skill skill = new Skill();
      skill.setName(faker.job().keySkills());
      skill.setExperienceLevel(faker.options().option("Beginner", "Intermediate", "Expert"));
      skill.setYearsOfExperience(faker.number().numberBetween(1, 21) + " years");
      skill.setJobSeeker(jobSeeker);
      skills.add(skill);
    }
    return skills;
  }

  private JobSeeker createJobSeeker(UserType jobSeekerType) {
    JobSeeker jobSeeker = new JobSeeker();
    createUser(jobSeekerType, jobSeeker);
    jobSeeker.setCity(faker.address().city());
    jobSeeker.setCountry(faker.address().country());
    jobSeeker.setEmploymentType(faker.job().field());
    jobSeeker.setFirstName(faker.name().firstName());
    jobSeeker.setLastName(faker.name().lastName());
    jobSeeker.setState(faker.address().state());
    jobSeeker.setSkills(createSkillsForJobSeeker(jobSeeker));
    jobSeeker.setWorkAuthorization(faker.options().option("US Citizen", "Canadian Citizen", "Green Card", "H1 Visa", "TN Permit"));
    return jobSeekerRepository.save(jobSeeker);
  }

  private Recruiter createRecruiter(UserType recruiterType) {
    Recruiter recruiter = new Recruiter();
    createUser(recruiterType, recruiter);
    recruiter.setCity(faker.address().city());
    recruiter.setCompany(faker.company().name());
    recruiter.setCountry(faker.address().country());
    recruiter.setFirstName(faker.name().firstName());
    recruiter.setLastName(faker.name().lastName());
    recruiter.setState(faker.address().state());
    return recruiterRepository.save(recruiter);
  }

  private void createUser(UserType userType, Object entity) {
    User user = new User();
    String email;
    do {
      email = faker.internet().emailAddress();
    } while (usedEmails.contains(email));
    usedEmails.add(email);

    user.setEmail(email);
    user.setActive(true);
    user.setPassword(passwordEncoder.encode("123"));
    user.setRegistrationDate(
      from(now().minusDays(faker.number().numberBetween(1, 365)).toInstant())
    );
    user.setUserType(userType);

    if (entity instanceof JobSeeker) {
      ((JobSeeker) entity).setUser(user);
    } else if (entity instanceof Recruiter) {
      ((Recruiter) entity).setUser(user);
    }

    userRepository.save(user);
  }

  private JobPost createPostActivity(Recruiter recruiter,
                                     List<Company> companies,
                                     List<Location> locations) {
    JobPost postActivity = new JobPost();
    postActivity.setDescriptionOfJob(faker.lorem().paragraph());
    postActivity.setJobTitle(faker.job().title());
    postActivity.setJobType(faker.options().option("Full-Time", "Part-Time", "Freelance"));
    postActivity.setPostedDate(
      from(now().minusDays(faker.number().numberBetween(1, 30)).toInstant())
    );
    postActivity.setRemote(faker.options().option("Remote-Only", "Office-Only", "Partial-Remote"));
    postActivity.setSalary(faker.number().digits(5));
    postActivity.setUser(recruiter.getUser());
    postActivity.setCompany(companies.get(faker.random().nextInt(companies.size())));
    postActivity.setLocation(locations.get(faker.random().nextInt(locations.size())));

    return jobPostRepository.save(postActivity);
  }

  private void createJobSeekerApply(JobSeeker jobSeeker, JobPost postActivity) {
    JobApplication jobSeekerApplies = new JobApplication();
    jobSeekerApplies.setApplyDate(
      from(now().minusDays(faker.number().numberBetween(1, 7)).toInstant())
    );
    jobSeekerApplies.setCoverLetter(faker.lorem().sentence(10));
    jobSeekerApplies.setJobPost(postActivity);
    jobSeekerApplies.setJobSeeker(jobSeeker);
    jobSeekerAppliesRepository.save(jobSeekerApplies);
  }

  private void createSavedJobsForJobSeeker(JobSeeker jobSeeker, JobPost postActivity) {
    int shouldSaveSavedJob = faker.number().numberBetween(1, 11);

    if (shouldSaveSavedJob % 5 == 0) {
      SavedJob savedJob = new SavedJob();
      savedJob.setJobSeeker(jobSeeker);
      savedJob.setJobPost(postActivity);
      savedJobRepository.save(savedJob);
    }
  }
}
