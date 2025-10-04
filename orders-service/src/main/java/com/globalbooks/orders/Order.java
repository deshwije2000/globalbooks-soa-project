package com.globalbooks.orders;

import java.util.List;

// Using record for a simple, immutable data carrier
public record Order(String orderId, String customerId, List<OrderItem> items) {}

record OrderItem(String isbn, int quantity) {}