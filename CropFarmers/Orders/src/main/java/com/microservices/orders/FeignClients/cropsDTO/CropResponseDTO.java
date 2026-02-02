package com.microservices.orders.FeignClients.cropsDTO;

public record CropResponseDTO(long cropId, String cropName, int quantity, double price) {
}
