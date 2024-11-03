package com.vawn.restaurant_management.service;

import com.vawn.restaurant_management.dto.request.RegisterRequest;
import com.vawn.restaurant_management.dto.request.SignInRequest;
import com.vawn.restaurant_management.dto.response.AuthResponse;
import com.vawn.restaurant_management.entity.User;
import com.vawn.restaurant_management.exception.UserException;

import java.util.List;

public interface UserService {
  void createDefaultAdmin();
  AuthResponse login(SignInRequest signInRequest) throws Exception;
  void createUser(RegisterRequest registerRequest) throws UserException;
  List<User> findAllUsers();
  User findUserByJwt(String jwt) throws UserException;
  void updatePassword(Long userId, String newPassword) throws UserException;
}
