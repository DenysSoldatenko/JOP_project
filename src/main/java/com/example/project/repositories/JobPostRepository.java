package com.example.project.repositories;

import com.example.project.dtos.RecruiterJobSummaryDto;
import com.example.project.entities.JobPost;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for {@link JobPost} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing post activities.
 */
public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

  @Query("""
      SELECT new com.example.project.dtos.RecruiterJobSummaryDto(
             COUNT(s.jobSeeker),
             j.jobPostId,
             j.jobTitle,
             l.id,
             l.city,
             l.state,
             l.country,
             c.id,
             c.name)
      FROM JobPost j
               INNER JOIN j.location l
               INNER JOIN j.company c
               LEFT JOIN j.applicants s
      WHERE j.user.id = :recruiter
      GROUP BY j.jobPostId, j.jobTitle, l.id, l.city, l.state, l.country, c.id, c.name
      """)
  List<RecruiterJobSummaryDto> getRecruiterJobs(@Param("recruiter") int recruiter);

  @Query("""
      SELECT j
      FROM JobPost j
               JOIN j.location l
      WHERE j.jobTitle LIKE CONCAT('%', :job, '%')
            AND (l.city LIKE CONCAT('%', :location, '%')
            OR l.country LIKE CONCAT('%', :location, '%')
            OR l.state LIKE CONCAT('%', :location, '%'))
            AND (:type IS NULL OR j.jobType IN :type)
            AND (:remote IS NULL OR j.remote IN :remote)
      """)
  List<JobPost> findJobsWithoutDate(@Param("job") String job,
                                    @Param("location") String location,
                                    @Param("remote") List<String> remote,
                                    @Param("type") List<String> type);

  @Query(value = """
      SELECT j
      FROM JobPost j
               JOIN j.location l
      WHERE j.jobTitle LIKE CONCAT('%', :job, '%')
            AND (l.city LIKE CONCAT('%', :location, '%')
            OR l.country LIKE CONCAT('%', :location, '%')
            OR l.state LIKE CONCAT('%', :location, '%'))
            AND (:type IS NULL OR j.jobType IN :type)
            AND (:remote IS NULL OR j.remote IN :remote)
            AND (j.postedDate >= :date)
      """)
  List<JobPost> findJobsWithDate(@Param("job") String job,
                                 @Param("location") String location,
                                 @Param("remote") List<String> remote,
                                 @Param("type") List<String> type,
                                 @Param("date") Date searchDate);
}
