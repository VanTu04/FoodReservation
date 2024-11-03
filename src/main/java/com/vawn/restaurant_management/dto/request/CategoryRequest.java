package com.vawn.restaurant_management.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryRequest {
  @NotEmpty(message = "Category's name cannot be empty")
  private String name;

  private String description;
}
