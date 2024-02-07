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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents a user entity in the system.
 */
@Data
@Entity
@Table(name = "users")
@NamedEntityGraph(name = "User.userType", attributeNodes = @NamedAttributeNode("userType"))
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Email
  @Column(name = "email", unique = true)
  private String email;

  @NotEmpty
  @Column(name = "password")
  private String password;

  @Column(name = "is_active")
  private boolean isActive;

  @Column(name = "registration_date")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date registrationDate;

  @ManyToOne
  @JoinColumn(name = "user_type_id")
  private UserType userType;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
  private Recruiter recruiter;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
  private JobSeeker jobSeeker;
}