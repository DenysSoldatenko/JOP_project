package com.example.jop_project.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

  @OneToMany(mappedBy = "userTypeId", cascade = CascadeType.ALL)
  private List<User> users;
}