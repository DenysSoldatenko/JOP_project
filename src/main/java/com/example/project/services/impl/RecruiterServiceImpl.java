package com.example.project.services.impl;

import static com.example.project.utils.ErrorMessages.RECRUITER_NOT_FOUND;
import static com.example.project.utils.FileStorageHelper.storeRecruiterPhoto;

import com.example.project.entities.Recruiter;
import com.example.project.entities.User;
import com.example.project.exceptions.RecruiterNotFoundException;
import com.example.project.repositories.RecruiterRepository;
import com.example.project.services.RecruiterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementation of the {@link RecruiterService} interface
 * that provides methods to manage {@link Recruiter} entities.
 */
@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

  private final RecruiterRepository recruiterRepository;

  @Override
  public Optional<Recruiter> findById(Integer id) {
    return recruiterRepository.findById(id);
  }

  @Override
  public void createRecruiter(User user, Recruiter recruiter, MultipartFile file) {
    storeRecruiterPhoto(user, recruiter, file);
    Recruiter existingRecruiter =  recruiterRepository.findById(user.getId())
        .orElseThrow(() -> new RecruiterNotFoundException(RECRUITER_NOT_FOUND + user.getId()));

    existingRecruiter.setFirstName(recruiter.getFirstName());
    existingRecruiter.setLastName(recruiter.getLastName());
    existingRecruiter.setCity(recruiter.getCity());
    existingRecruiter.setState(recruiter.getState());
    existingRecruiter.setCountry(recruiter.getCountry());
    existingRecruiter.setCompany(recruiter.getCompany());
    existingRecruiter.setProfilePhoto(recruiter.getProfilePhoto());

    recruiterRepository.save(existingRecruiter);
  }
}
