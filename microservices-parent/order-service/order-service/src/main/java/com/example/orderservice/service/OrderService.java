package com.example.orderservice.service;

import com.example.orderservice.dto.OrderLineItemDtoIn;
import com.example.orderservice.dto.OrderRequestDtoIn;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItem;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDtoIn orderRequestDtoIn) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItemList = orderRequestDtoIn.getOrderLineItemDtoInList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItemList);
        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDtoIn orderLineItemDtoIn) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDtoIn.getPrice());
        orderLineItem.setQuantity(orderLineItemDtoIn.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDtoIn.getSkuCode());
        return orderLineItem;
    }
}
