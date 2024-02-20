package com.example.project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.Data;

/**
 * Entity class representing a saved job by a job seeker.
 */
@Data
@Entity(name = "saved_jobs")
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"jobSeeker", "postActivity"})
})
public class SavedJob implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "job_seeker_id")
  private JobSeeker jobSeeker;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "post_activity_id")
  private PostActivity postActivity;
}