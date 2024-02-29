package com.example.project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents a post activity entity in the system.
 */
@Data
@Entity
@Table(name = "post_activities")
@NamedEntityGraph(
    name = "JobPost.companyAndLocation",
    attributeNodes = {
      @NamedAttributeNode("company"),
      @NamedAttributeNode("location")
    }
)
public class JobPost {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer jobPostId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "company_id")
  private Company company;

  @Transient
  private Boolean isActive;

  @Transient
  private Boolean isSaved;

  @Length(max = 10000)
  private String descriptionOfJob;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date postedDate;

  @OneToMany(mappedBy = "jobPost")
  private List<JobApplication> applicants;

  private String jobType;
  private String salary;
  private String remote;
  private String jobTitle;
}