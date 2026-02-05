package com.microservices.cart.cartDTO;

import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsResponseDTO;
import com.microservices.cart.cartModel.CartItems;

import java.util.List;

public record CartResponseDTO(long cartId, String userEmailId, double totalPrice, List<CartItemsResponseDTO> items) {
}
