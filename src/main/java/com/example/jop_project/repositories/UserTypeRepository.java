package com.example.jop_project.repositories;

import com.example.jop_project.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link UserType} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing user types.
 */
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}