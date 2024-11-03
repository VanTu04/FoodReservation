package com.vawn.restaurant_management.repository;

import com.vawn.restaurant_management.entity.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface FoodRepository extends JpaRepository<Food, Long> {
  boolean existsByName(String name);
}
