package com.example.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents a post activity entity in the system.
 */
@Data
@Entity
@Table(name = "post_activities")
public class PostActivity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "users_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "locations_id")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "companies_id")
  private Company company;

  @Transient
  private Boolean isActive;

  @Transient
  private Boolean isSaved;

  @Length(max = 10000)
  private String descriptionOfJob;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date postedDate;

  private String jobType;
  private String salary;
  private String remote;
  private String jobTitle;
}