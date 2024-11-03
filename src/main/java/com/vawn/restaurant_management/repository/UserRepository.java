package com.vawn.restaurant_management.repository;

import com.vawn.restaurant_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhoneNumber(String phoneNumber);
  boolean existsByPhoneNumber(String phoneNumber);
}
