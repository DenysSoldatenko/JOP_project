package com.example.project.repositories;

import com.example.project.entities.PostActivity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link PostActivity} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing post activities.
 */
public interface PostActivityRepository extends JpaRepository<PostActivity, Integer> {
}