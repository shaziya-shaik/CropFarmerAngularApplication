package com.microservices.login.FeignClients.cartDTO;



import com.microservices.login.FeignClients.cartDTO.cartItemsDTO.CartItemsResponseDTO;

import java.util.List;

public record CartResponseDTO(long cartId, String userEmailId, double totalPrice, List<CartItemsResponseDTO> items) {
}
