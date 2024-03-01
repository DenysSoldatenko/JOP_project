package com.example.project.initializers;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.SavedJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for initializing the database with sample data.
 *
 * <p>Note: Ensure that in the {@link JobApplication}, {@link SavedJob}, and {@link JobPost} entities,
 * the relationships defined with {@code CascadeType.ALL} are updated to {@code @ManyToOne}
 * to ensure proper data insertion during seeding.</p>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseInitializer {

  private final UserDataInitializer userDataInitializer;
  private final JobDataInitializer jobDataInitializer;

  @Bean
  CommandLineRunner initData() {
    return args -> {
      log.info("Initializing database with sample data...");
      userDataInitializer.seedUsers();
      jobDataInitializer.seedJobData();
      log.info("Database initialization completed.");
    };
  }
}
