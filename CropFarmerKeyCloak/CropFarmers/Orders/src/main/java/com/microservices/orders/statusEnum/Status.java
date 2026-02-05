package com.microservices.orders.statusEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Status {
    CREATED,
    PENDING,
    PROCESSING,
    DELIVERED,
    CANCELLED,
    FAILED,
    SHIPPED,
}
