package com.example.project.repositories;

import com.example.project.entities.JobApplication;
import com.example.project.entities.JobPost;
import com.example.project.entities.JobSeeker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

  List<JobApplication> findByJobSeeker(JobSeeker jobSeeker);

  List<JobApplication> findByJobPost(JobPost jobPost);
}