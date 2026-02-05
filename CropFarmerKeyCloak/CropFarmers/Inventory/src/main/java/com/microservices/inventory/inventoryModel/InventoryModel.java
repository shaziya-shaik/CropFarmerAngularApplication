package com.microservices.inventory.inventoryModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryModel {
    @Id
    private long cropId;
    private int availableQuantity;
}
