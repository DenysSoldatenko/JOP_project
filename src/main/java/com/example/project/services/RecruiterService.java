package com.example.project.services;

import com.example.project.entities.Recruiter;
import java.util.Optional;

public interface RecruiterService {
  Optional<Recruiter> findById(Integer id);
}
