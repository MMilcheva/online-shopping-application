package com.example.orderservice.service;

import brave.Span;
import brave.Tracer;
import com.example.orderservice.dto.InventoryResponseDtoOut;
import com.example.orderservice.dto.OrderLineItemDtoIn;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.event.OrderPlacedEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItem;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoInList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemList);



        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItem::getSkuCode)
                .toList();

        log.info("Calling inventory services");

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");


        try(Tracer.SpanInScope spanInScope=tracer.withSpanInScope(inventoryServiceLookup.start())){
            //acc to the video must be only withSpan
            InventoryResponseDtoOut[] inventoryResponseDtoOutArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponseDtoOut[].class)//Boolean is the type of the response we are looking for
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponseDtoOutArray).allMatch(InventoryResponseDtoOut::isInStock);
            if (allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return "Order placed successfully!";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        }finally{
            inventoryServiceLookup.finish();  //acc to the video must be end()
        }

        //Call Inventory stock and place an order if product is in stock
        //synchronous request - defined by block()

    }

    private OrderLineItem mapToDto(OrderLineItemDtoIn orderLineItemDtoIn) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDtoIn.getPrice());
        orderLineItem.setQuantity(orderLineItemDtoIn.getQuantity());
        orderLineItem.setSkuCode(orderLineItemDtoIn.getSkuCode());
        return orderLineItem;
    }
}
