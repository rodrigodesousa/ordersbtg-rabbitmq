package com.rodrigosousa.ordermsbtg.controller.dto;

import com.rodrigosousa.ordermsbtg.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Nested
    class FromEntity {

        @Test
        void shouldMapCorrectly() {
            //Arrange
            var input = OrderEntityFactory.build();

            //Act
            var output = OrderResponse.fromEntity(input);

            //Assert
            assertEquals(input.getOrderId(), output.orderId());
            assertEquals(input.getCustomerId(), output.customerId());
            assertEquals(input.getTotal(), output.total());
        }
    }

}