package com.microservices.inventory.inventoryService;

import com.microservices.inventory.inventoryDTOs.InventoryRequestDTO;
import com.microservices.inventory.inventoryDTOs.InventoryResponseDTO;
import com.microservices.inventory.inventoryModel.InventoryModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    void createInventory(long cropId , int quantity);
    int reduceInventory(long cropId , int quantity);

    int restoreInventory(long cropId , int quantity);

    List<InventoryResponseDTO> getInventory();

    InventoryResponseDTO getInventorybyId(long cropId);

    boolean deleteInventory(long cropId);
}
