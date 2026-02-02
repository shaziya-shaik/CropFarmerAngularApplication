package com.microservices.cart.FeignClients.cropsDTO;


public record CropRequestDTO(String cropName, int quantity, double price) {
}
