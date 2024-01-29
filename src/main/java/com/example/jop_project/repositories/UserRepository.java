package com.example.jop_project.repositories;

import com.example.jop_project.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link User} entities.
 * This interface extends {@link JpaRepository} to provide basic CRUD operations
 * and additional methods for querying and managing users.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

  @EntityGraph(value = "User.userType", type = EntityGraph.EntityGraphType.FETCH)
  Optional<User> findByEmail(String email);
}