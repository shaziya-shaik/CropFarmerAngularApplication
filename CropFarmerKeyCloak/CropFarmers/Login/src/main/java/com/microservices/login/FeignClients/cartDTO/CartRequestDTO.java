package com.microservices.login.FeignClients.cartDTO;


import com.microservices.login.FeignClients.cartDTO.cartItemsDTO.CartItemsResponseDTO;

import java.util.List;

public record CartRequestDTO( String userEmailId, double totalPrice, List<CartItemsResponseDTO> items) {
}
