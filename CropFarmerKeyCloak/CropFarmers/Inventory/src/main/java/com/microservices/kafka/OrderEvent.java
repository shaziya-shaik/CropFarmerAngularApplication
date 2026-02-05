package com.microservices.kafka;

import java.util.List;

public record OrderEvent(long orderId, String type, List<OrderItemEvent> items) {
}
