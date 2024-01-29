package com.example.jop_project.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a type of user in the system.
 */
@Data
@Entity
@Table(name = "users_type")
public class UserType {

  @Id
  @Column(name = "user_type_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userTypeId;

  @Column(name = "user_type_name")
  private String userTypeName;

  @ToString.Exclude
  @OneToMany(mappedBy = "userTypeId", cascade = CascadeType.ALL)
  private List<User> users;
}