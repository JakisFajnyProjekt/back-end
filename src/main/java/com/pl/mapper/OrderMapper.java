package com.pl.mapper;

import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;

import java.util.List;

public class OrderMapper {

    public Order mapToOrder(OrderDTO orderDto) {
        return new Order(
                orderDto.id(),
                orderDto.isCompleted(),
                orderDto.date(),
                orderDto.price()
        );
    }

    public OrderDTO mapToOrderDto(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getIsCompleted(),
                order.getDate(),
                order.getPrice()
        );
    }

    public List<OrderDTO> mapToListDto(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .toList();
    }
}
