package com.vawn.restaurant_management.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data //toString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodRequest {
  @NotBlank(message = "Name is Required")
  @Size(min = 3,max=100,message = "Title must be between 3 and 200 characters")
  private String name;

  @Min(value=0,message = "price must be greater than or equal 0")
  @Max(value=1000000000,message = "price must be less than or equal 1000000000")
  private Double price;

  private String description;

  private boolean available;

  private String thumbnail;

  @JsonProperty("category_id")
  private Long categoryId;

  private MultipartFile files;
}
