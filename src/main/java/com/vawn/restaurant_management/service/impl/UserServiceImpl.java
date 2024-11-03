package com.vawn.restaurant_management.service.impl;

import com.vawn.restaurant_management.utils.JwtTokenUtil;
import com.vawn.restaurant_management.dto.request.RegisterRequest;
import com.vawn.restaurant_management.dto.request.SignInRequest;
import com.vawn.restaurant_management.dto.response.AuthResponse;
import com.vawn.restaurant_management.entity.Role;
import com.vawn.restaurant_management.entity.User;
import com.vawn.restaurant_management.exception.UserException;
import com.vawn.restaurant_management.repository.RoleRepository;
import com.vawn.restaurant_management.repository.UserRepository;
import com.vawn.restaurant_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  public void createDefaultAdmin() {

  }

  @Override
  public AuthResponse login(SignInRequest signInRequest) throws Exception {
    var existingUser = userRepository.findByPhoneNumber(signInRequest.getPhonenumber());

    if(existingUser.isEmpty()){
      throw new UserException("Invalid phone number or password");
    }

    User user = existingUser.get();

    if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())){
      throw new UserException("Username or password is incorrect");
    }

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getPhonenumber(), signInRequest.getPassword(), user.getAuthorities()));
    String accessToken = jwtTokenUtil.generateToken(user);

    return AuthResponse.builder()
            .jwt(accessToken)
            .message("success")
            .roles(existingUser.get().getRole().getRoleName())
            .build();
  }

  @Override
  public void createUser(RegisterRequest registerRequest) throws UserException {
    String phoneNumber = registerRequest.getPhoneNumber();
    if(userRepository.existsByPhoneNumber(phoneNumber)){
      throw new UserException("Phone number already in use");
    }
    Long roleId = registerRequest.getRoleId();
    Role role = roleRepository.findById(roleId).orElseThrow(() -> new UserException("Role not found"));
    if(role.getRoleName().equals("ROLE_ADMIN")){
      throw new UserException("You cannot register an admin account");
    }
    User user = User.builder()
            .fullName(registerRequest.getFullName())
            .phoneNumber(registerRequest.getPhoneNumber())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .active(true)
            .role(role)
            .build();

    userRepository.save(user);
  }

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll().stream().toList();
  }

  @Override
  public User findUserByJwt(String jwt) throws UserException {
    String phoneNumber = jwtTokenUtil.extractPhoneNumber(jwt);

    Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
    if (user.isPresent()) {
      return user.get();
    }
    else {
      throw new UserException("User not exist with phone number " + phoneNumber);
    }
  }

  @Override
  public void updatePassword(Long userId, String newPassword) throws UserException {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException("User not exist with phone number " + userId));
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }
}
