package com.microservices.orders.orderService.orderServiceImp;


import com.microservices.orders.orderModel.OrderModel;
import com.microservices.orders.orderRepository.OrderRepo;
import com.microservices.orders.statusEnum.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusScheduler {

    private final OrderRepo orderRepo;

    // Every 5 seconds → CREATED → PROCESSING
    @Scheduled(fixedRate = 5000)
    public void moveToProcessing() {
        List<OrderModel> orders = orderRepo.findByOrderStatus(Status.CREATED);

        for (OrderModel order : orders) {
            order.setOrderStatus(Status.PROCESSING);
            orderRepo.save(order);
        }
    }

    // After 1 minute → PROCESSING → DELIVERED
    @Scheduled(fixedRate = 60000)
    public void moveToDelivered() {
        List<OrderModel> orders = orderRepo.findByOrderStatus(Status.PROCESSING);

        for (OrderModel order : orders) {
            order.setOrderStatus(Status.DELIVERED);
            orderRepo.save(order);
        }
    }
}

