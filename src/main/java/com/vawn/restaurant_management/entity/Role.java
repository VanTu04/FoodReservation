package com.vawn.restaurant_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@jakarta.persistence.Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "role_name", nullable = false)
  private String roleName;

  public static String ADMIN = "ADMIN";
  public static String MANAGER = "MANAGER";
  public static String CUSTOMER = "CUSTOMER";
}
