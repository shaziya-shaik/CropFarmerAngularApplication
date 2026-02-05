package com.microservices.cart.cartService;

import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsRequestDTO;
import com.microservices.cart.cartDTO.CartResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartResponseDTO getCartItems(String userEmailId);
    void addCartItems(CartItemsRequestDTO cartItemsRequestDTO , String userEmailId) ;
    boolean deleteCartItem(long cartItemId , String userEmailId);
     void deleteCart(String userEmailId);

    void createCart(String userEmailId);
}
