package com.example.jop_project.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userTypeId", referencedColumnName = "user_type_id")
  private UserType userTypeId;
}