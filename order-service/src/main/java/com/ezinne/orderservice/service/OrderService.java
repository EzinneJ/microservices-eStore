package com.ezinne.orderservice.service;

import com.ezinne.orderservice.model.InventoryResponse;
import com.ezinne.orderservice.model.Order;
import com.ezinne.orderservice.model.OrderItems;
import com.ezinne.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public Order placeOrder(Order incomingOrder) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItemsList(incomingOrder.getOrderItemsList());

        List<String> skuCodes = order.getOrderItemsList().stream()
                .map(OrderItems::getSkuCode)
                .toList();

//        call inventory service and place order if in stock
        InventoryResponse[] inventoryResponses = restTemplate.getForObject(
                "http://localhost:8084/api/v1/inventory" + skuCodes, InventoryResponse[].class);


        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if (allProductsInStock) {
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is out of stock, please try again later");
        }
    }

    public void cancelOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
