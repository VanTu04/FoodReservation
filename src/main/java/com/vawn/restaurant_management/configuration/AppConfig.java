package com.vawn.restaurant_management.configuration;

import com.vawn.restaurant_management.entity.Role;
import com.vawn.restaurant_management.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@ComponentScan
@RequiredArgsConstructor
public class AppConfig {
  private final JwtTokenFilter jwtTokenFilter;

  @Value("${api.prefix}")
  private String apiPrefix;

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8500")
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed request headers
                .allowCredentials(false)
                .maxAge(3600);
      }
    };
  }
  @Bean
  public SecurityFilterChain configure(@NonNull HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers(POST,
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix)
                            )
                            .permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/category", apiPrefix),
                                    String.format("%s/food", apiPrefix))
                            .permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/category/**", apiPrefix),
                                    String.format("%s/food/**", apiPrefix),
                                    String.format("%s/food/uploads/**", apiPrefix))
                            .hasRole("ADMIN")
                            .requestMatchers(PUT,
                                    String.format("%s/category/**", apiPrefix),
                                    String.format("%s/food/**", apiPrefix))
                            .hasRole("ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/category/**", apiPrefix),
                                    String.format("%s/food/**", apiPrefix))
                            .hasRole("ADMIN")
                            .anyRequest().authenticated());
    return http.build();
  }
}
