package com.example.project.services;

import com.example.project.entities.Recruiter;
import java.util.Optional;

/**
 * Service interface for managing operations related to {@link Recruiter} entities.
 */
public interface RecruiterService {
  Optional<Recruiter> findById(Integer id);
}
