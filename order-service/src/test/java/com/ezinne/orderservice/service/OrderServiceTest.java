package com.ezinne.orderservice.service;

import com.ezinne.orderservice.model.Order;
import com.ezinne.orderservice.model.OrderItems;
import com.ezinne.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void canPlaceOrder() {
        Order order = new Order(1L,"1wsdhriy",
                List.of(new OrderItems(1L, "Utaba_1", new BigDecimal(20.00), 1),
                        new OrderItems(2L, "Utaba_1", new BigDecimal(10.00), 1)));
        orderRepository.save(order);

        when(orderRepository.save(any())).thenReturn(order);
        Order placedOrder = orderService.placeOrder(order);

        verify(orderRepository, times(2)).save(any());
        assertThat(placedOrder).hasFieldOrProperty("orderNumber");

    }

    @Test
    void canCancelOrder() {
        Order order = new Order(1L,"1wsdhriy",
                List.of(new OrderItems(1L, "Utaba_1", new BigDecimal(20.00), 1),
                        new OrderItems(2L, "Utaba_1", new BigDecimal(10.00), 1)));
        orderRepository.save(order);


        orderService.cancelOrder(1L);
        Optional<Order> cancelledOrder = orderRepository.findById(1L);

        verify(orderRepository, times(1)).deleteById(anyLong());
        assertThat(cancelledOrder.isEmpty());
}
}