package com.microservices.orders.kafkaProducer;

import com.microservices.kafka.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    private  final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendOrderPlaced(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }

    public void sendOrderCancelled(OrderEvent event) {
        kafkaTemplate.send("order-events", event);
    }
}
