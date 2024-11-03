package com.vawn.restaurant_management.entity;

import com.vawn.restaurant_management.enums.RESERVATION_STATUS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@jakarta.persistence.Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
public class TableReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "guest_count",nullable = false)
  private int guestCount;

  @Enumerated(EnumType.STRING)
  private RESERVATION_STATUS status;

  @Column(name = "reservation_method", nullable = false)
  private String reservationMethod; // "online" hoặc "by-staff"

  @Column(name = "reservation_time")
  private LocalDateTime reservationTime;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private User customer;

  @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Order> orders;
}
