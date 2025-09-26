package com.rodrigosousa.ordermsbtg.service;

import com.rodrigosousa.ordermsbtg.entity.OrderEntity;
import com.rodrigosousa.ordermsbtg.factory.OrderCreatedEventFactory;
import com.rodrigosousa.ordermsbtg.factory.OrderEntityFactory;
import com.rodrigosousa.ordermsbtg.repository.OrderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<OrderEntity> orderEntityArgumentCaptor;

    @Nested
    class Save {

        @Test
        void shouldCallRepositorySave() {
            //Arrange
            var event = OrderCreatedEventFactory.buildWithOneItem();

            //Act
            orderService.save(event);

            //Assert
            verify(orderRepository, times(1)).save(any());
        }

        @Test
        void shouldMapEventToEntityWithSuccess() {
            //Arrange
            var event = OrderCreatedEventFactory.buildWithOneItem();

            //Act
            orderService.save(event);

            //Assert
            verify(orderRepository, times(1)).save(orderEntityArgumentCaptor.capture());
            var entity = orderEntityArgumentCaptor.getValue();
            assertEquals(event.codigoPedido(), entity.getOrderId());
            assertEquals(event.codigoCliente(), entity.getCustomerId());
            assertNotNull(entity.getTotal());
            assertEquals(event.itens().getFirst().produto(), entity.getItems().getFirst().getProduct());
            assertEquals(event.itens().getFirst().quantidade(), entity.getItems().getFirst().getQuantity());
            assertEquals(event.itens().getFirst().preco(), entity.getItems().getFirst().getPrice());
        }

        @Test
        void shouldCalculateOrderTotalWithSuccess() {
            //Arrange
            var event = OrderCreatedEventFactory.buildWithTwoItems();
            var totalItem1 = event.itens().getFirst().preco().multiply(BigDecimal.valueOf(event.itens().getFirst().quantidade()));
            var totalItem2 = event.itens().getLast().preco().multiply(BigDecimal.valueOf(event.itens().getLast().quantidade()));
            var orderTotal = totalItem1.add(totalItem2);

            //Act
            orderService.save(event);

            //Assert
            verify(orderRepository, times(1)).save(orderEntityArgumentCaptor.capture());
            var entity = orderEntityArgumentCaptor.getValue();

            assertNotNull(entity.getTotal());
            assertEquals(orderTotal, entity.getTotal());
        }
    }

    @Nested
    class FindAllByCustomerId {
        @Test
        void shouldCallRepositoryFindAllByCustomerId() {
            //Arrange
            var customerId = 1L;
            var pageRequest = PageRequest.of(0, 10);
            doReturn(OrderEntityFactory.buildWithPage())
                    .when(orderRepository).findAllByCustomerId(eq(customerId), eq(pageRequest));

            //Act
            orderService.findAllByCustomerId(customerId, pageRequest);

            //Assert
            verify(orderRepository, times(1)).findAllByCustomerId(eq(customerId), eq(pageRequest));
        }

        @Test
        void shouldMapResponse() {
            //Arrange
            var customerId = 1L;
            var pageRequest = PageRequest.of(0, 10);
            var page = OrderEntityFactory.buildWithPage();
            doReturn(page)
                    .when(orderRepository).findAllByCustomerId(anyLong(), any());

            //Act
            var response = orderService.findAllByCustomerId(customerId, pageRequest);

            //Assert
            assertEquals(page.getTotalElements(), response.getTotalElements());
            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());
            assertEquals(page.getContent().getFirst().getOrderId(), response.getContent().getFirst().orderId());
            assertEquals(page.getContent().getFirst().getCustomerId(), response.getContent().getFirst().customerId());
            assertEquals(page.getContent().getFirst().getTotal(), response.getContent().getFirst().total());
        }
    }
}