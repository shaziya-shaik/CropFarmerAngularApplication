package com.microservices.crops.FeignClients.inventoryDTOs;

public record InventoryResponseDTO(long cropId, int availableQuantity) {
}
