package com.example.project.repositories;

import com.example.project.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Company} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing companies.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}