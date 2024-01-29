package com.example.jop_project.services;

import com.example.jop_project.entities.UserType;
import java.util.List;

/**
 * Service interface for operations related to {@link UserType}.
 */
public interface UserTypeService {

  List<UserType> getAllUserTypes();
}