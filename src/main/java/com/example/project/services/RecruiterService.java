package com.example.project.services;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for managing operations related to {@link Recruiter} entities.
 */
public interface RecruiterService {
  Optional<Recruiter> findById(Integer id);

  void createRecruiter(User user, Recruiter recruiter, MultipartFile file);
}
