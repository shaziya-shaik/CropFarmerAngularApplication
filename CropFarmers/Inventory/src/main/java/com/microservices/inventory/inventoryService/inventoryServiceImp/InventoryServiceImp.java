package com.microservices.inventory.inventoryService.inventoryServiceImp;


import com.microservices.inventory.inventoryDTOs.InventoryResponseDTO;
import com.microservices.inventory.inventoryModel.InventoryModel;
import com.microservices.inventory.inventoryRepository.InventoryRepo;
import com.microservices.inventory.inventoryService.InventoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImp implements InventoryService {


    private final InventoryRepo repo;

    public InventoryServiceImp( InventoryRepo repo) {
        this.repo = repo;
    }


    @Override
    public void createInventory(long cropId, int quantity) {
        InventoryModel inventory=new InventoryModel();
        inventory.setCropId(cropId);
        inventory.setAvailableQuantity(quantity);
        repo.save(inventory);

    }

    @Override
    public int reduceInventory(long cropId, int quantity) {
        InventoryModel inventory = repo.findByCropId(cropId)
                .orElseGet(() -> {
                    InventoryModel inv = new InventoryModel();
                    inv.setCropId(cropId);
                    inv.setAvailableQuantity(0);
                    return repo.save(inv);
                });

        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() - quantity
        );

        return repo.save(inventory).getAvailableQuantity();
    }

    @Override
    public int restoreInventory(long cropId, int quantity) {
        InventoryModel inventory=  repo.findByCropId(cropId).orElseThrow(()-> new RuntimeException("Inventory not found"));

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + quantity);
        repo.save(inventory);
        return inventory.getAvailableQuantity();



    }

    @Override
    public List<InventoryResponseDTO> getInventory() {
        List<InventoryModel> inventories = repo.findAll();
        List<InventoryResponseDTO>  inventoriesDTO = new ArrayList<InventoryResponseDTO>();
        for (InventoryModel inventory : inventories) {
            inventoriesDTO.add(mapToInventoryResponseDTO(inventory));
        }
        return inventoriesDTO;

    }

    @Override
    public InventoryResponseDTO getInventorybyId(long cropId) {
        InventoryModel inventory = repo.findByCropId(cropId)
                .orElse(new InventoryModel(cropId, 0));

        return new InventoryResponseDTO(
                inventory.getCropId(),
                inventory.getAvailableQuantity()
        );
    }

    @Override
    public boolean deleteInventory(long cropId) {
        InventoryModel inventory=  repo.findByCropId(cropId).orElse(null);
        if(inventory!=null){
            repo.delete(inventory);
        }
        return false;
    }

    private InventoryResponseDTO mapToInventoryResponseDTO(InventoryModel inventory) {
       return  new InventoryResponseDTO(inventory.getCropId(), inventory.getAvailableQuantity());
    }
}
