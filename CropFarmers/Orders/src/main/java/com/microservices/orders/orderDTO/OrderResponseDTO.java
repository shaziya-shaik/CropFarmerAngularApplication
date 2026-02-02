package com.microservices.orders.orderDTO;

import com.microservices.orders.orderDTO.orderItemsDTO.OrderItemsResponseDTO;

import com.microservices.orders.statusEnum.Status;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(long orderId , String userEmailId , LocalDateTime orderDate , Status orderStatus , double totalAmount , List<OrderItemsResponseDTO> orderItems) {
}
