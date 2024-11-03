package com.vawn.restaurant_management.controller;

import com.vawn.restaurant_management.dto.request.FoodImageRequest;
import com.vawn.restaurant_management.dto.request.FoodRequest;
import com.vawn.restaurant_management.entity.Food;
import com.vawn.restaurant_management.entity.FoodImage;
import com.vawn.restaurant_management.exception.CategoryException;
import com.vawn.restaurant_management.exception.FoodException;
import com.vawn.restaurant_management.service.CategoryService;
import com.vawn.restaurant_management.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/food")
public class FoodController {

  private final FoodService foodService;
//  private final CategoryService categoryService;


  @GetMapping("")
//  public String getFoods(){
//    return "ok";
//  }
  public ResponseEntity<List<Food>> getFoods() {
    List<Food> foodList = foodService.getAllProducts();
    return ResponseEntity.status(HttpStatus.OK).body(foodList);
  }

  @PostMapping("")
  public ResponseEntity<?> createProduct(@Valid @RequestBody FoodRequest foodRequest, BindingResult result) {
    try {
      if(result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      Food newFood = foodService.createFood(foodRequest);
      return ResponseEntity.ok(newFood);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

//  upload anh cho food
  @PostMapping(value = "/uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImages(@PathVariable("id") Long foodId,
                                        @RequestParam("files") List<MultipartFile> files){
    try {
      Food existingFood = foodService.getFoodById(foodId);
      files = files == null ? new ArrayList<MultipartFile>() : files;

      List<FoodImage> foodImages = new ArrayList<>();
      for (MultipartFile file : files) {
        if(file.getSize() == 0) {
          continue;
        }
        // Kiểm tra kích thước file và định dạng
        if(file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
          return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                  .body("File is too large! Maximum size is 10MB");
        }
        String contentType = file.getContentType();
        if(contentType == null || !contentType.startsWith("image/")) {
          return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                  .body("File must be an image");
        }
        // Lưu file và cập nhật thumbnail trong DTO
        String filename = storeFile(file);
        //lưu vào đối tượng product trong DB
        FoodImage foodImage = foodService
                .createFoodImage(existingFood.getId(),
                        FoodImageRequest.builder()
                                .imageUrl(filename)
                                .build()
                );
        foodImages.add(foodImage);
      }
      return ResponseEntity.ok().body(foodImages);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private String storeFile(MultipartFile file) throws IOException{
    if (!isImageFile(file) || file.getOriginalFilename() == null) {
      throw new IOException("Invalid image format");
    }
    String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
    String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;

    java.nio.file.Path uploadDir = Paths.get("uploads");
    if(!Files.exists(uploadDir)){
      Files.createDirectories(uploadDir);
    }
    java.nio.file.Path destination = Paths.get(uploadDir.toString(),uniqueFilename);
    Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
    return uniqueFilename;
  }

  private boolean isImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && contentType.startsWith("image/");
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateFood(@PathVariable Long id,
                                      @ModelAttribute FoodRequest foodRequest) throws IOException, FoodException, CategoryException {
    foodService.updateFood(id, foodRequest);
    return ResponseEntity.status(HttpStatus.OK).body("Food updated successfully");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFood(@PathVariable Long id) throws FoodException {
    foodService.deleteFood(id);
    return ResponseEntity.status(HttpStatus.OK).body("Food deleted successfully");
  }
}
