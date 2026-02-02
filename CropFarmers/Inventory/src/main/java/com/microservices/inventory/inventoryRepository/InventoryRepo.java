package com.microservices.inventory.inventoryRepository;

import com.microservices.inventory.inventoryModel.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<InventoryModel, Long> {
    Optional<InventoryModel> findByCropId(long cropId);
}
