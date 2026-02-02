package com.microservices.inventory.inventoryService.KafkaConsumer;

import com.microservices.inventory.inventoryService.InventoryService;
import com.microservices.kafka.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryKafkaConsumer {

    private final InventoryService inventoryService;

    public InventoryKafkaConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consume(OrderEvent event) {

        if ("ORDER_PLACED".equals(event.type())) {
            event.items().forEach(item ->
                    inventoryService.reduceInventory(
                            item.cropId(),
                            item.quantity()
                    )
            );
        }

        if ("ORDER_CANCELLED".equals(event.type())) {
            event.items().forEach(item ->
                    inventoryService.restoreInventory(
                            item.cropId(),
                            item.quantity()
                    )
            );
        }
    }
}
