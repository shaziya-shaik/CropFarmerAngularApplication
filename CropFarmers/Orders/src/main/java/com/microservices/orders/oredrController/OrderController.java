package com.microservices.orders.oredrController;

import com.microservices.orders.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.orders.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.orders.orderDTO.OrderResponseDTO;
import com.microservices.orders.orderDTO.orderItemsDTO.OrderItemsResponseDTO;
import com.microservices.orders.orderModel.OrderItem;
import com.microservices.orders.orderService.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@EnableScheduling
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }


    @GetMapping("/cart/cartItems")
    public ResponseEntity<CartResponseDTO> getCartByUser(@RequestParam String userEmailId) {
        return  new ResponseEntity<>(service.getCartByUser(userEmailId), HttpStatus.OK);

    }

    @PostMapping("/cart/cartItems")
    public ResponseEntity<Void> addCartItems(@RequestBody CartItemsRequestDTO cartItemsRequestDTO, @RequestParam String userEmailId) {
        service.addCartItems(cartItemsRequestDTO , userEmailId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/cart/deleteItem/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(@PathVariable long cartItemId, @RequestParam String userEmailId) {
        boolean isDeleted = service.deleteCartItem(cartItemId, userEmailId);
        if(isDeleted){
            return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/getOrderItems")
    public ResponseEntity<List<OrderResponseDTO>> getOrderItems(@RequestParam String userEmailId){

        return  new ResponseEntity<>(service.getOrderItems(userEmailId), HttpStatus.OK);


    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Void> placeOrder (@RequestParam  String userEmailId){
        service.placeOrder(userEmailId);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        service.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled");
    }
}

