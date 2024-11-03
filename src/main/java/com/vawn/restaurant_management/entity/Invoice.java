package com.vawn.restaurant_management.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "invoice")
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "order_id", nullable = false, unique = true)
  private Order order;

  @Column(name = "total_amount")
  private BigDecimal totalAmount; // Tổng số tiền

  @Column(name = "payment_method")
  private String paymentMethod; // Phương thức thanh toán

  @Column(name = "payment_time")
  private LocalDateTime paymentTime; // Thời gian thanh toán
}
