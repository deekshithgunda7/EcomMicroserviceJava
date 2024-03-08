package com.springmicroservicepoc.orderservice.service;

import com.springmicroservicepoc.orderservice.dto.OrderLineItemsDto;
import com.springmicroservicepoc.orderservice.dto.OrderRequest;
import com.springmicroservicepoc.orderservice.model.Order;
import com.springmicroservicepoc.orderservice.model.OrderLineItems;
import com.springmicroservicepoc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);
        orderRepository.save(order);
    }


        private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
            OrderLineItems orderLineItems = new OrderLineItems();
            orderLineItems.setPrice(orderLineItemsDto.getPrice());
            orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
            orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
            return orderLineItems;
        }
}
