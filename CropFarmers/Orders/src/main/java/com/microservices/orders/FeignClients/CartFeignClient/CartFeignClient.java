package com.microservices.orders.FeignClients.CartFeignClient;



import com.microservices.orders.FeignClients.cartDTO.CartResponseDTO;
import com.microservices.orders.FeignClients.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CART-SERVICE" , url="http://localhost:8083")
public interface CartFeignClient {

    @GetMapping("/cart/cartItems")
    CartResponseDTO getCartByUser(@RequestParam String userEmailId) ;

    @PostMapping("/cart/cartItems")
    void addCartItems(@RequestBody CartItemsRequestDTO cartItemsRequestDTO, @RequestParam String userEmailId) ;

    @DeleteMapping("/cart/deleteItem/{cartItemId}")
    Boolean deleteCartItem(@PathVariable long cartItemId, @RequestParam String userEmailId) ;

    @DeleteMapping("/cart/DeleteCart")
    public ResponseEntity<Void> deleteCart(@RequestParam String userEmailId);


}
