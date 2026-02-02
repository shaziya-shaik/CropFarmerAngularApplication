package com.microservices.login.FeignClients.cropsDTO;


public record CropRequestDTO(String cropName, int quantity, double price) {
}
