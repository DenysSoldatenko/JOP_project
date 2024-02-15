package com.example.project.repositories;

import com.example.project.dtos.RecruiterJobDto;
import com.example.project.entities.PostActivity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for {@link PostActivity} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing post activities.
 */
public interface PostActivityRepository extends JpaRepository<PostActivity, Integer> {

  @Query("""
          SELECT new com.example.project.dtos.RecruiterJobDto(
                 COUNT(s.user_id),
                 j.id,
                 j.jobTitle,
                 l.id,
                 l.city,
                 l.state,
                 l.country,
                 c.id,
                 c.name)
          FROM PostActivity j
                   INNER JOIN j.location l
                   INNER JOIN j.company c
                   LEFT JOIN j.applicants s
          WHERE j.user.id = :recruiter
          GROUP BY j.id, j.jobTitle, l.id, l.city, l.state, l.country, c.id, c.name
          """)
  List<RecruiterJobDto> getRecruiterJobs(@Param("recruiter") int recruiter);
}