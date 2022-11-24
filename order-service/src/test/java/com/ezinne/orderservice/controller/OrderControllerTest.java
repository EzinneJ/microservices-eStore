package com.ezinne.orderservice.controller;

import com.ezinne.orderservice.model.Order;
import com.ezinne.orderservice.model.OrderItems;
import com.ezinne.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @Autowired
    private OrderController orderController;

    @Autowired
    private ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).build();
    }

    @Test
    void placeOrder() throws Exception {
        Order order = new Order(1L,"1wsdhriy",
                List.of(new OrderItems(1L, "436bdjk", new BigDecimal(20.00), 1),
                        new OrderItems(2L, "7dtbmfkjf", new BigDecimal(10.00), 1)));
               orderService.placeOrder(order);

               this.mockMvc.perform(post("/api/v1/order")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(order)))
                       .andExpect(status().isCreated())
                       .andReturn();
               verify(orderService, times(2)).placeOrder(any());
    }

    @Test
    void cancelOrder() throws Exception {
        Order order = new Order(1L,"1wsdhriy",
                List.of(new OrderItems(1L, "436bdjk", new BigDecimal(20.00), 1),
                        new OrderItems(2L, "7dtbmfkjf", new BigDecimal(10.00), 1)));
        orderService.placeOrder(order);


        this.mockMvc.perform(delete("/api/v1/order/2"))
                .andExpect(status().isNotFound())
                .andReturn();
        assertThat(order.getOrderItemsList().size() == 1);
    }

}