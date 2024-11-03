package com.vawn.restaurant_management.dto.request;

import lombok.Data;

@Data
public class SignInRequest {
  private String phonenumber;
  private String password;
}
