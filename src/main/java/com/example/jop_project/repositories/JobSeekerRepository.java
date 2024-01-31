package com.example.jop_project.repositories;

import com.example.jop_project.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link JobSeeker} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing job seekers.
 */
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Integer> {
}