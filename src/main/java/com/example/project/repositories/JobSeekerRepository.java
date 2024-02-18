package com.example.project.repositories;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

import com.example.project.entities.JobSeeker;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for managing {@link JobSeeker} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing job seekers.
 */
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {

  @Query("SELECT j FROM JobSeeker j WHERE j.jobSeekerId = :id")
  @EntityGraph(value = "JobSeeker.skills", type = FETCH)
  Optional<JobSeeker> findWithSkillsById(Integer id);
}