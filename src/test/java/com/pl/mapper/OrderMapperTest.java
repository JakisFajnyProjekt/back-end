package com.pl.mapper;

import com.pl.model.Order;
import com.pl.model.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderMapperTest {
    @InjectMocks
    private OrderMapper orderMapper;
    Order order;
    OrderDTO orderDto;
    OrderDTO expectedDto;

    @BeforeEach
    void testData() {
        order = new Order(true, new BigDecimal("10.00"));
        orderDto = new OrderDTO(true, LocalDateTime.now(), new BigDecimal("10.00"));
        expectedDto = new OrderDTO(true, LocalDateTime.now(), new BigDecimal("10.00"));
    }

    @Test
    void shouldMapToDto() {
        //Given
        //When
        OrderDTO attemptOrderDto = orderMapper.mapToOrderDto(order);
        //Then
        assertEquals(expectedDto.isCompleted(), attemptOrderDto.isCompleted());
        assertEquals(expectedDto.date().truncatedTo(ChronoUnit.HOURS), attemptOrderDto.date().truncatedTo(ChronoUnit.HOURS));
        assertEquals(expectedDto.price(), attemptOrderDto.price());
    }

    @Test
    void shouldMapFromDto() {
        //Given
        //When
        Order attemptOrder = orderMapper.mapToOrder(orderDto);
        //Then
        assertEquals(expectedDto.isCompleted(), attemptOrder.getIsCompleted());
        assertEquals(expectedDto.date().truncatedTo(ChronoUnit.HOURS), attemptOrder.getDate().truncatedTo(ChronoUnit.HOURS));
        assertEquals(expectedDto.price(), attemptOrder.getTotalPrice());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        List<Order> orders = List.of(
                new Order(true, new BigDecimal("10.00")),
                new Order(true, new BigDecimal("10.00")),
                new Order(true, new BigDecimal("10.00"))
        );
        //When
        List<OrderDTO> attemptList = orderMapper.mapToListDto(orders);
        //Then
        assertEquals(3, attemptList.size());
        assertEquals(OrderDTO.class, attemptList.get(0).getClass());
    }
}
