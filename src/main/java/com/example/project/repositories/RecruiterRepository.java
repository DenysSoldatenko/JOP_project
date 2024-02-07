package com.example.project.repositories;

import com.example.project.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Recruiter} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing recruiters.
 */
public interface RecruiterRepository extends JpaRepository<Recruiter, Integer> {
}