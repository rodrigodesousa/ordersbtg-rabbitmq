package com.rodrigosousa.ordermsbtg.controller;

import com.rodrigosousa.ordermsbtg.controller.dto.ApiResponse;
import com.rodrigosousa.ordermsbtg.controller.dto.OrderResponse;
import com.rodrigosousa.ordermsbtg.controller.dto.PaginationResponse;
import com.rodrigosousa.ordermsbtg.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@PathVariable("customerId")Long customerId,
                                                                 @RequestParam(name="page", defaultValue="0") Integer page,
                                                                 @RequestParam(name="size", defaultValue="10") Integer size) {
        var body = orderService.findAllByCustomerId(customerId, PageRequest.of(page, size));

        return ResponseEntity.ok(new ApiResponse<>(
                    body.getContent(),
                    PaginationResponse.fromPage(body)
                ));
    }
}
