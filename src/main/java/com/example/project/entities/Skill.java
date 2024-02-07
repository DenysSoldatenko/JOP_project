package com.example.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing a skill associated with a job seeker.
 */
@Data
@Entity
@Table(name = "skills")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private String experienceLevel;
  private String yearsOfExperience;

  @ManyToOne
  @JoinColumn(name = "job_seeker_id")
  private JobSeeker jobSeeker;
}