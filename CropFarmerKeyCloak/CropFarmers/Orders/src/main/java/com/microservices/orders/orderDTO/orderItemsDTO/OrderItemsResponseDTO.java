package com.microservices.orders.orderDTO.orderItemsDTO;

public record OrderItemsResponseDTO(long orderItemId , long cropId , String cropName , int quantity , double priceAtOrderTime , double subTotal ) {
}
