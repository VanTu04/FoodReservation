package com.vawn.restaurant_management.service.impl;

import com.vawn.restaurant_management.dto.request.FoodImageRequest;
import com.vawn.restaurant_management.dto.request.FoodRequest;
import com.vawn.restaurant_management.entity.Category;
import com.vawn.restaurant_management.entity.Food;
import com.vawn.restaurant_management.entity.FoodImage;
import com.vawn.restaurant_management.exception.DataNotFoundException;
import com.vawn.restaurant_management.exception.FoodException;
import com.vawn.restaurant_management.repository.CategoryRepository;
import com.vawn.restaurant_management.repository.FoodImageRepository;
import com.vawn.restaurant_management.repository.FoodRepository;
import com.vawn.restaurant_management.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
  private final FoodRepository foodRepository;
  private final CategoryRepository categoryRepository;
  private final FoodImageRepository foodImageRepository;

  @Override
  public Food createFood(FoodRequest foodRequest) throws FoodException {
    Category existingCategory = categoryRepository.findById(foodRequest.getCategoryId())
            .orElseThrow(() -> new FoodException("Category not found"));

    Food newFood = Food.builder()
            .name(foodRequest.getName())
            .price(foodRequest.getPrice())
            .thumbnail(foodRequest.getThumbnail())
            .description(foodRequest.getDescription())
            .category(existingCategory)
            .available(foodRequest.isAvailable())
            .build();
    return foodRepository.save(newFood);
  }

  @Override
  public List<Food> getAllProducts() {
    return foodRepository.findAll();
  }

  @Override
  public Food getFoodById(Long id) throws FoodException {
    return foodRepository.findById(id)
            .orElseThrow(() -> new FoodException("Food not found"));
  }

  @Override
  public Food updateFood(Long id, FoodRequest foodRequest) throws FoodException {
    Food existingFood = getFoodById(id);

    if(existingFood != null) {
      Category existtingCategory = categoryRepository.findById(foodRequest.getCategoryId())
              .orElseThrow(() -> new FoodException("Category not found"));
      existingFood.setName(foodRequest.getName());
      existingFood.setPrice(foodRequest.getPrice());
      existingFood.setThumbnail(foodRequest.getThumbnail());
      existingFood.setDescription(foodRequest.getDescription());
      existingFood.setCategory(existtingCategory);
      existingFood.setAvailable(foodRequest.isAvailable());
      return foodRepository.save(existingFood);
    }
    return null;
  }

  @Override
  public void deleteFood(Long id) throws FoodException {
    Food existingFood = foodRepository.findById(id)
            .orElseThrow(() -> new FoodException("Food not found"));
    foodRepository.delete(existingFood);
  }

  @Override
  public boolean existsByName(String name) {
    return foodRepository.existsByName(name);
  }

  @Override
  public FoodImage createFoodImage(Long foodId, FoodImageRequest foodImageRequest) throws Exception {
    Food existingFood = foodRepository.findById(foodId)
            .orElseThrow(() -> new DataNotFoundException("Cannot find food"));
    FoodImage foodImage = FoodImage.builder()
            .food(existingFood)
            .imageUrl(foodImageRequest.getImageUrl())
            .build();
    return foodImageRepository.save(foodImage);
  }
}
