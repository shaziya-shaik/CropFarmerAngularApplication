package com.microservices.crops.FeignClients.client;

import com.microservices.crops.FeignClients.inventoryDTOs.InventoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "INVENTORY-SERVICE" , url="http://localhost:8085")
public interface InventoryClient {

    @PostMapping("/inventory/create")
    void createInventory(@RequestParam long cropId, @RequestParam int quantity);
    @PutMapping("/inventory/restore/{cropId}/{quantity}")
    int restoreInventory(@PathVariable long cropId, @PathVariable int quantity);
    @GetMapping("/inventory/cropId")
    InventoryResponseDTO getInventroyById(@RequestParam long cropId);

    @PutMapping("/inventory/reduce/{cropId}/{quantity}")
    int reduceInventory(@PathVariable long cropId, @PathVariable int quantity);

    @DeleteMapping("/inventory/delete")
    boolean deleteInventory(long cropId);
}
