package com.pl.mapper;

import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Order mapToOrder(OrderDTO orderDto) {
        return new Order(
                orderDto.isCompleted(),
                orderDto.price()
        );
    }

    public OrderDTO mapToOrderDto(Order order) {
        return new OrderDTO(
                order.getIsCompleted(),
                order.getCreatedAt(),
                order.getTotalCost()
        );
    }

    public List<OrderDTO> mapToListDto(List<Order> orders) {
        return orders.stream()
                .map(this::mapToOrderDto)
                .toList();
    }
}
