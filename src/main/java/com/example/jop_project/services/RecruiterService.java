package com.example.jop_project.services;

import com.example.jop_project.entities.Recruiter;
import java.util.Optional;

public interface RecruiterService {
  Optional<Recruiter> findById(Integer id);
}
