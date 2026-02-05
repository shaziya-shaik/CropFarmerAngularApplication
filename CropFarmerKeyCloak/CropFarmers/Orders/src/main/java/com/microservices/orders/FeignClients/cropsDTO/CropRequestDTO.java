package com.microservices.orders.FeignClients.cropsDTO;


public record CropRequestDTO(String cropName, int quantity, double price) {
}
