package com.example.project.services.impl;

import com.example.project.entities.Recruiter;
import com.example.project.repositories.RecruiterRepository;
import com.example.project.services.RecruiterService;
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
