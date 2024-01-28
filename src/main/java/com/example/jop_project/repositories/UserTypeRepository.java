package com.example.jop_project.repositories;

import com.example.jop_project.entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}