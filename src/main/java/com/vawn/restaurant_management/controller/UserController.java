package com.vawn.restaurant_management.controller;

import com.vawn.restaurant_management.dto.request.RegisterRequest;
import com.vawn.restaurant_management.dto.request.SignInRequest;
import com.vawn.restaurant_management.dto.response.AuthResponse;
import com.vawn.restaurant_management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
    try {
      if(result.hasErrors()){
        List<String> errorMessages = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      if(!registerRequest.getPassword().equals(registerRequest.getRetypePassword())){
        return ResponseEntity.badRequest().body("Password not match");
      }

      userService.createUser(registerRequest);
      return ResponseEntity.ok("");
    } catch (Exception e){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody SignInRequest request) throws Exception {
    try {
      AuthResponse authResponse = userService.login(request);
      return ResponseEntity.ok(authResponse);
    }catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
  }


}
