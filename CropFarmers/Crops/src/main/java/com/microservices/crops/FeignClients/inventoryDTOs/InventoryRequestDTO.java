package com.microservices.crops.FeignClients.inventoryDTOs;

public record InventoryRequestDTO (long cropId, int availableQuantity) {};
