package com.vawn.restaurant_management.configuration;

import com.vawn.restaurant_management.entity.Role;
import com.vawn.restaurant_management.entity.User;
import com.vawn.restaurant_management.repository.RoleRepository;
import com.vawn.restaurant_management.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminAccountInitializer {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;

  @PostConstruct
  public void initAdminAccount() {
    if(!userRepository.existsByPhoneNumber("admin")){
      Role adminRole = new Role();
      adminRole.setRoleName("ROLE_ADMIN");

      var role = roleRepository.findByRoleName("ROLE_ADMIN");
      if (role.isEmpty()) {
        roleRepository.save(adminRole); // Lưu vai trò admin nếu chưa có
      } else {
        adminRole = role.get(); // Lấy vai trò admin nếu đã tồn tại
      }
      User user = User.builder()
              .fullName("admin")
              .email("admin@gmail.com")
              .password(passwordEncoder.encode("0987654321"))
              .phoneNumber("admin")
              .active(true)
              .role(adminRole)
              .build();
      userRepository.save(user);
      System.out.println("Admin Account created");
    }
  }
}
