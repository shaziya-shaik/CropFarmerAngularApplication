package com.microservices.cart.cartDTO.cartItemsDTO;

public record CartItemsResponseDTO(long cartItemId , long cropId , String cropName, int quantity , double price , double subTotal ) {
}
