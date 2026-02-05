package com.microservices.cart.cartDTO;

import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsResponseDTO;


import java.util.List;

public record CartRequestDTO( String userEmailId, double totalPrice, List<CartItemsResponseDTO> items) {
}
