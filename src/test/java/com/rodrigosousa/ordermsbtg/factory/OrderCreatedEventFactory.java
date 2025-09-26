package com.rodrigosousa.ordermsbtg.factory;

import com.rodrigosousa.ordermsbtg.listener.dto.OrderCreatedEvent;
import com.rodrigosousa.ordermsbtg.listener.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent buildWithOneItem(){
        var items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var event = new OrderCreatedEvent(1L, 2L, List.of(items));

        return event;
    }

    public static OrderCreatedEvent buildWithTwoItems(){
        var items1 = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var items2 = new OrderItemEvent("mouse", 1, BigDecimal.valueOf(35.25));
        var event = new OrderCreatedEvent(1L, 2L, List.of(items1, items2));

        return event;
    }
}
