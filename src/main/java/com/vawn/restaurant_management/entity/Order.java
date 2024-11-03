package com.vawn.restaurant_management.entity;

import com.vawn.restaurant_management.enums.ORDER_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@jakarta.persistence.Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "order_time")
  private LocalDateTime orderTime;

  @Enumerated(EnumType.STRING)
  private ORDER_STATUS status;

  private BigDecimal totalPrice;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;

  @ManyToOne
  @JoinColumn(name = "reservation_id", nullable = false)
  private TableReservation reservation;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  List<OrderItem> orderItems;
}
