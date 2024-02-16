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
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entity class representing the application of a job seeker for a job post activity.
 */
@Data
@Entity(name = "job_seeker_applies")
@Table(uniqueConstraints = {
  @UniqueConstraint(columnNames = {"jobSeekerId", "postActivityId"})
})
public class JobSeekerApply implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "job_seeker_id")
  private JobSeeker jobSeekerId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "post_activity_id")
  private PostActivity postActivityId;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date applyDate;

  private String coverLetter;
}