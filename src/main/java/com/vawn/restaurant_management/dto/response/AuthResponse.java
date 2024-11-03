package com.vawn.restaurant_management.dto.response;

import com.vawn.restaurant_management.enums.ROLE_USER;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthResponse {
  private String message;
  private String jwt;
  private String roles;
}
