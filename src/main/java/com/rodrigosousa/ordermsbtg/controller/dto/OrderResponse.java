package com.rodrigosousa.ordermsbtg.controller.dto;

import com.rodrigosousa.ordermsbtg.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(Long orderId, Long customerId, BigDecimal total) {

    public static OrderResponse fromEntity(OrderEntity order) {
        return new OrderResponse(order.getOrderId(), order.getCustomerId(), order.getTotal());
    }
}
