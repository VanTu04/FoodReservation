package com.vawn.restaurant_management.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  @NotBlank(message = "Full name is required")
  @JsonProperty("full_name")
  private String fullName;

  @Email(message = "Invalid email")
  private String email;

  @NotBlank(message = "User name is required")
  @JsonProperty("phone_number")
  private String phoneNumber;

  private String address;

  @NotBlank(message = "Password can not be blank")
  private String password;

  @JsonProperty("retype_password")
  private String retypePassword;

  @NotNull(message = "Role id is required")
  @JsonProperty("role_id")
  private Long roleId;
}