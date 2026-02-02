package com.microservices.cart.FeignClients.cropsDTO;

public record CropResponseDTO(long cropId, String cropName, int quantity, double price) {
}
