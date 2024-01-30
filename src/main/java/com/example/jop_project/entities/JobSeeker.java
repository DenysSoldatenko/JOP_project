package com.example.jop_project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Entity class representing a job seeker in the system.
 */
@Data
@Entity
@Table(name = "job_seekers")
public class JobSeeker {

  @Id
  private int jobSeekerId;

  @MapsId
  @OneToOne
  @JoinColumn(name = "job_seeker_id")
  private User userId;

  private String firstName;
  private String lastName;
  private String city;
  private String state;
  private String country;
  private String workAuthorization;
  private String employmentType;
  private String resume;

  @Column(length = 64)
  private String profilePhoto;

  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobSeekerId")
  private List<Skill> skills;
}