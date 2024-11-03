package com.vawn.restaurant_management.service;

import com.vawn.restaurant_management.dto.request.CategoryRequest;
import com.vawn.restaurant_management.entity.Category;
import com.vawn.restaurant_management.entity.Food;

import java.util.List;

public interface CategoryService {
  Category createCategory(CategoryRequest categoryRequest);
  List<Food> getAllFoods();
  List<Category> getAllCategories();
  Category getCategoryExists(Long categoryId);
  Category updateCategory(Long categoryId, CategoryRequest categoryRequest);
  void deleteCategory(Long id);
}
