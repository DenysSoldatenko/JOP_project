package com.example.jop_project.repositories;

import com.example.jop_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}