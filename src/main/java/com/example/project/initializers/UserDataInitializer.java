package com.example.project.initializers;

import static java.time.ZonedDateTime.now;
import static java.util.Date.from;
import static java.util.stream.IntStream.range;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.Recruiter;
import com.example.project.entities.Skill;
import com.example.project.entities.User;
import com.example.project.entities.UserType;
import com.example.project.repositories.JobSeekerRepository;
import com.example.project.repositories.RecruiterRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.repositories.UserTypeRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Component responsible for seeding user data into the database.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataInitializer {

  private final Faker faker = new Faker();
  private final Set<String> usedEmails = new HashSet<>();

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserTypeRepository userTypeRepository;
  private final JobSeekerRepository jobSeekerRepository;
  private final RecruiterRepository recruiterRepository;

  /**
   * Seeds user data into the database, including job seekers and recruiters.
   */
  public void seedUsers() {
    log.info("Starting user data seeding...");

    UserType jobSeekerType = createUserType("Job Seeker");
    UserType recruiterType = createUserType("Recruiter");

    range(0, 10).forEach(i -> createJobSeeker(jobSeekerType));
    range(0, 10).forEach(i -> createRecruiter(recruiterType));

    log.info("User data seeding completed.");
  }

  private UserType createUserType(String typeName) {
    log.info("Creating user type: {}", typeName);
    UserType userType = new UserType();
    userType.setUserTypeName(typeName);
    return userTypeRepository.save(userType);
  }

  private void createUser(UserType userType, Object entity) {
    log.info("Creating user with type: {}", userType.getUserTypeName());
    User user = new User();
    String email;
    do {
      email = faker.internet().emailAddress();
    } while (usedEmails.contains(email));
    usedEmails.add(email);

    user.setEmail(email);
    user.setActive(true);
    user.setPassword(passwordEncoder.encode("123"));
    user.setRegistrationDate(from(now().minusDays(faker.number().numberBetween(1, 365)).toInstant()));
    user.setUserType(userType);

    if (entity instanceof JobSeeker) {
      ((JobSeeker) entity).setUser(user);
    } else if (entity instanceof Recruiter) {
      ((Recruiter) entity).setUser(user);
    }

    userRepository.save(user);
    log.info("User created with email: {}", email);
  }

  private void createJobSeeker(UserType jobSeekerType) {
    log.info("Creating job seeker...");
    JobSeeker jobSeeker = new JobSeeker();
    createUser(jobSeekerType, jobSeeker);
    jobSeeker.setCity(faker.address().city());
    jobSeeker.setCountry(faker.address().country());
    jobSeeker.setEmploymentType(faker.options().option("Full-Time", "Part-Time", "Freelance"));
    jobSeeker.setFirstName(faker.name().firstName());
    jobSeeker.setLastName(faker.name().lastName());
    jobSeeker.setState(faker.address().state());
    jobSeeker.setSkills(createSkillsForJobSeeker(jobSeeker));
    jobSeeker.setWorkAuthorization(faker.options().option("US Citizen", "Canadian Citizen", "Green Card", "H1 Visa", "TN Permit"));
    jobSeekerRepository.save(jobSeeker);
    log.info("Job seeker created with email: {}", jobSeeker.getUser().getEmail());
  }

  private void createRecruiter(UserType recruiterType) {
    log.info("Creating recruiter...");
    Recruiter recruiter = new Recruiter();
    createUser(recruiterType, recruiter);
    recruiter.setCity(faker.address().city());
    recruiter.setCompany(faker.company().name());
    recruiter.setCountry(faker.address().country());
    recruiter.setFirstName(faker.name().firstName());
    recruiter.setLastName(faker.name().lastName());
    recruiter.setState(faker.address().state());
    recruiterRepository.save(recruiter);
    log.info("Recruiter created with email: {}", recruiter.getUser().getEmail());
  }

  private List<Skill> createSkillsForJobSeeker(JobSeeker jobSeeker) {
    log.info("Creating skills for job seeker: {}", jobSeeker.getUser().getEmail());
    int numberOfSkills = faker.number().numberBetween(1, 4);
    List<Skill> skills = new ArrayList<>();
    for (int i = 0; i < numberOfSkills; i++) {
      Skill skill = new Skill();
      skill.setName(faker.job().keySkills());
      skill.setExperienceLevel(faker.options().option("Beginner", "Intermediate", "Advance"));
      skill.setYearsOfExperience(faker.number().numberBetween(1, 21) + " years");
      skill.setJobSeeker(jobSeeker);
      skills.add(skill);
    }
    return skills;
  }
}
