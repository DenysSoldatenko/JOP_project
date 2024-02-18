package com.example.project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Data;

/**
 * Entity class representing a job seeker in the system.
 */
@Data
@Entity
@Table(name = "job_seekers")
@NamedEntityGraph(name = "JobSeeker.skills", attributeNodes = @NamedAttributeNode("skills"))
public class JobSeeker {

  @Id
  @Column(name = "job_seeker_id")
  private int jobSeekerId;

  @MapsId
  @OneToOne
  @JoinColumn(name = "job_seeker_id")
  private User user;

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

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobSeeker")
  private List<Skill> skills;

  @Transient
  public String getPhotosImagePath() {
    return (profilePhoto == null) ? null : "/photos/candidate/" + jobSeekerId + "/" + profilePhoto;
  }

  /**
   * Sets the user associated with job seeker.
   */
  public void setUser(User user) {
    this.user = user;
    if (user != null && user.getJobSeeker() != this) {
      user.setJobSeeker(this);
    }
  }
}