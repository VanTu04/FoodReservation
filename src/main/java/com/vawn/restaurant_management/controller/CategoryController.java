package com.vawn.restaurant_management.controller;

import com.vawn.restaurant_management.dto.request.CategoryRequest;
import com.vawn.restaurant_management.entity.Category;
import com.vawn.restaurant_management.exception.UserException;
import com.vawn.restaurant_management.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/category")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("")
  public ResponseEntity<List<Category>> getAllCategories() {
    List<Category> categories = categoryService.getAllCategories();
    return ResponseEntity.ok(categories);
  }

  @PostMapping("")
  public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest,
                                          BindingResult result) throws UserException {
    if(result.hasErrors()) {
      List<String> errorMessages = result.getFieldErrors()
              .stream()
              .map(FieldError::getDefaultMessage)
              .toList();
      return ResponseEntity.badRequest().body(errorMessages);
    }
    categoryService.createCategory(categoryRequest);
    return ResponseEntity.ok("Category created");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateCategory(
          @PathVariable Long id,
          @Valid @RequestBody CategoryRequest categoryRequest
  ) {
    categoryService.updateCategory(id, categoryRequest);
    return ResponseEntity.ok("Update category successfully");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
    Category category = categoryService.getCategoryExists(id);
    if(category != null) {
      categoryService.deleteCategory(id);
      return ResponseEntity.ok("Delete category with id: " + id + " successfully");
    }else {
      return ResponseEntity.notFound().build();
    }
  }
}
