package com.example.project.repositories;

import com.example.project.entities.JobSeeker;
import com.example.project.entities.SavedJob;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for {@link SavedJob} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing saved jobs.
 */
public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {

  List<SavedJob> findByJobSeeker(JobSeeker jobSeeker);

  @Query(value = """
      SELECT CASE WHEN COUNT(sj) > 0 THEN true ELSE false END
      FROM post_activities pa
               JOIN jop.saved_jobs sj on pa.id = sj.post_activity_id
               JOIN job_seekers js ON sj.job_seeker_id = js.job_seeker_id
      WHERE pa.id = :jobPostId and js.job_seeker_id = :jobSeekId
      """, nativeQuery = true)
  boolean existsSavedJobByJobPostIdAndJobSeekerId(@Param("jobPostId") Integer jobPostId,
                                                  @Param("jobSeekId") Integer jobSeekId);
}