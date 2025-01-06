package com.kibernumacademy.authserver.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kibernumacademy.authserver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String name);
}
