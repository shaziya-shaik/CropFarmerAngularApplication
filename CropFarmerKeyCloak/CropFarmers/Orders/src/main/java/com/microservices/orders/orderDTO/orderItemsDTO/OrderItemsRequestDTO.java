package com.microservices.orders.orderDTO.orderItemsDTO;

public record OrderItemsRequestDTO(long cropId , String cropName , int quantity , double priceAtOrderTime , double subTotal) {
}
