package com.microservices.orders.orderRepository;

import com.microservices.orders.orderDTO.OrderResponseDTO;
import com.microservices.orders.orderDTO.orderItemsDTO.OrderItemsResponseDTO;
import com.microservices.orders.orderModel.OrderModel;
import com.microservices.orders.statusEnum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findByUserEmailId(String userEmailId);

    List<OrderModel> findByOrderStatus(Status status);
}
