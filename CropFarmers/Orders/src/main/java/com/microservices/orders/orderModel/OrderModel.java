package com.microservices.orders.orderModel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.microservices.orders.statusEnum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orderTable")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String userEmailId;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private Status orderStatus;
    private double totalAmount;
    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

}
