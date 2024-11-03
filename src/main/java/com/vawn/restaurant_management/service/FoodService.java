package com.vawn.restaurant_management.service;

import com.vawn.restaurant_management.dto.request.FoodImageRequest;
import com.vawn.restaurant_management.dto.request.FoodRequest;
import com.vawn.restaurant_management.entity.Food;
import com.vawn.restaurant_management.entity.FoodImage;
import com.vawn.restaurant_management.exception.FoodException;

import java.util.List;

public interface FoodService {
  Food createFood(FoodRequest foodRequest) throws Exception;
  List<Food> getAllProducts();
  Food getFoodById(Long id) throws FoodException;
  Food updateFood(Long id, FoodRequest foodRequest) throws FoodException;
  void deleteFood(Long id) throws FoodException;
  boolean existsByName(String name);
  FoodImage createFoodImage(Long foodId, FoodImageRequest foodImageRequest) throws Exception;
}
