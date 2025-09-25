package com.rodrigosousa.ordermsbtg.service;

import com.rodrigosousa.ordermsbtg.controller.dto.OrderResponse;
import com.rodrigosousa.ordermsbtg.entity.OrderEntity;
import com.rodrigosousa.ordermsbtg.entity.OrderItem;
import com.rodrigosousa.ordermsbtg.listener.dto.OrderCreatedEvent;
import com.rodrigosousa.ordermsbtg.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event){
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getTotal(event));

        orderRepository.save(entity);
    }

    private List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream().map(item -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(item.produto());
            orderItem.setQuantity(item.quantidade());
            orderItem.setPrice(item.preco());
            return orderItem;
        }).toList();
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest){
        return orderRepository.findAllByCustomerId(customerId, pageRequest)
                .map(OrderResponse::fromEntity);
    }
}
