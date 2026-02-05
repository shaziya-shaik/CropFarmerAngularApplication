package com.microservices.orders.FeignClients.cartDTO.cartItemsDTO;

public record CartItemsResponseDTO(long cartItemId , long cropId , int quantity , double price , double subTotal) {
}
