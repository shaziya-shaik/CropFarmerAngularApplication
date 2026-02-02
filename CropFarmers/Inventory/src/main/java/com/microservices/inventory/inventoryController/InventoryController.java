package com.microservices.inventory.inventoryController;

import com.microservices.inventory.inventoryDTOs.InventoryResponseDTO;
import com.microservices.inventory.inventoryService.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService service;


    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createInventory(@RequestParam long cropId , @RequestParam int quantity) {
        service.createInventory(cropId , quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getInventroy() {
       List<InventoryResponseDTO>  inventories= service.getInventory();
       return ResponseEntity.ok().body(inventories);

    }

    @GetMapping("/cropId")
    public ResponseEntity<InventoryResponseDTO> getInventroyById(@RequestParam long cropId) {
        InventoryResponseDTO  inventories= service.getInventorybyId(cropId);
        return ResponseEntity.ok().body(inventories);

    }

    @PutMapping("/reduce/{cropId}/{quantity}")
    public ResponseEntity<Integer> reduceInventory(@PathVariable long cropId, @PathVariable int quantity) {
        int q=service.reduceInventory(cropId , quantity);
        return ResponseEntity.ok().body(q);
    }

    @PutMapping("/restore/{cropId}/{quantity}")
    public ResponseEntity<Integer> restoreInventory(@PathVariable long cropId, @PathVariable int quantity) {
        int q=service.restoreInventory(cropId , quantity);
        return ResponseEntity.ok().body(q);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteInventory( @RequestParam  long cropId){
        boolean b=service.deleteInventory(cropId);
        return ResponseEntity.ok().body(b);


    }
}
