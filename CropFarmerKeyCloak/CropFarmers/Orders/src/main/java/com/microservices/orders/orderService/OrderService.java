package com.microservices.orders.orderService;

import com.microservices.orders.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.orders.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.orders.orderDTO.OrderResponseDTO;
import com.microservices.orders.orderDTO.orderItemsDTO.OrderItemsResponseDTO;
import com.microservices.orders.orderModel.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    CartResponseDTO getCartByUser(String userEmailId);

    void addCartItems(CartItemsRequestDTO cartItemsRequestDTO, String userEmailId);

    List<OrderResponseDTO> getOrderItems(String userEmailId);

    boolean deleteCartItem(long cartItemId, String userEmailId);

    void placeOrder(String userEmailId);
     void cancelOrder(Long orderId);
}
