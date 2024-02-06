package com.example.jop_project.services.impl;

import com.example.jop_project.entities.Recruiter;
import com.example.jop_project.repositories.RecruiterRepository;
import com.example.jop_project.services.RecruiterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

  private final RecruiterRepository recruiterRepository;

  @Override
  public Optional<Recruiter> findById(Integer id) {
    return recruiterRepository.findById(id);
  }
}
