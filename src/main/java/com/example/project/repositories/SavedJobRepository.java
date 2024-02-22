package com.example.project.repositories;

import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link SavedJob} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing saved jobs.
 */
public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {

  List<SavedJob> findByJobSeeker(JobSeeker jobSeeker);

  List<SavedJob> findByJobPost(JobPost jobPost);
}