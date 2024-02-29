package com.example.project.repositories;

import com.example.project.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Location} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing locations.
 */
public interface LocationRepository extends JpaRepository<Location, Integer> {
}