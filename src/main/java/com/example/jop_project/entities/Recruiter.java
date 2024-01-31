package com.example.jop_project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity class representing a recruiter in the system.
 */
@Data
@Entity
@Table(name = "recruiters")
public class Recruiter {

  @Id
  @Column(name = "recruiter_id")
  private int recruiterId;

  @MapsId
  @OneToOne
  @JoinColumn(name = "recruiter_id")
  private User user;

  private String firstName;
  private String lastName;
  private String city;
  private String state;
  private String country;
  private String company;

  @Column(length = 64)
  private String profilePhoto;

  /**
   * Sets the user associated with recruiter.
   */
  public void setUser(User user) {
    this.user = user;
    if (user != null && user.getRecruiter() != this) {
      user.setRecruiter(this);
    }
  }
}