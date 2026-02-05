package com.microservices.kafka;

public record OrderItemEvent(long cropId, int quantity) {
}
