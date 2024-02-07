package com.example.project.services.impl;

import com.example.project.entities.UserType;
import com.example.project.repositories.UserTypeRepository;
import com.example.project.services.UserTypeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserTypeService} interface.
 * This service provides operations related to user types.
 */
@Service
@RequiredArgsConstructor
public class UserTypeServiceImpl implements UserTypeService {

  private final UserTypeRepository userTypeRepository;

  @Override
  public List<UserType> getAllUserTypes() {
    return userTypeRepository.findAll();
  }
}