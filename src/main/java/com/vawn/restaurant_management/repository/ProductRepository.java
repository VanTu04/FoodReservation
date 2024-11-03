package com.vawn.restaurant_management.repository;

import com.vawn.restaurant_management.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Food, Long> {
}
