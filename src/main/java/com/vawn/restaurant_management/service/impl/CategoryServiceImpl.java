package com.vawn.restaurant_management.service.impl;

import com.vawn.restaurant_management.dto.request.CategoryRequest;
import com.vawn.restaurant_management.entity.Category;
import com.vawn.restaurant_management.entity.Food;
import com.vawn.restaurant_management.exception.DataNotFoundException;
import com.vawn.restaurant_management.repository.CategoryRepository;
import com.vawn.restaurant_management.repository.ProductRepository;
import com.vawn.restaurant_management.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;

  @Override
  public Category createCategory(CategoryRequest categoryRequest) {
    Category category = Category.builder()
            .name(categoryRequest.getName())
            .description(categoryRequest.getDescription())
            .build();
    return categoryRepository.save(category);
  }

  @Override
  public List<Food> getAllFoods() {
    return productRepository.findAll();
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Category getCategoryExists(Long categoryId) {
    return categoryRepository.findById(categoryId).orElseThrow(() -> new DataNotFoundException("Category not found"));
  }

  @Override
  public Category updateCategory(Long categoryId, CategoryRequest categoryRequest) {
    Category category = getCategoryExists(categoryId);
    category.setName(categoryRequest.getName());
    category.setDescription(categoryRequest.getDescription());
    return categoryRepository.save(category);
  }

  @Override
  public void deleteCategory(Long id) {
    categoryRepository.deleteById(id);
  }

}
