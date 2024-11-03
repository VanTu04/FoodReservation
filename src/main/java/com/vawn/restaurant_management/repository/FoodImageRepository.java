package com.vawn.restaurant_management.repository;

import com.vawn.restaurant_management.entity.FoodImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodImageRepository extends JpaRepository<FoodImage, Long> {
}
