package com.epam.esm.core.service;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.OrderRequestDto;

public interface OrderService {

    /**
     * Creates an order with customer and certificates, stated in orderReQuestDto
     * @param orderRequestDto dto containing order data
     * @return created order
     */
    OrderDto placeOrder(OrderRequestDto orderRequestDto);

    /**
     * Retrieves an order by id
     * @param id id to find an order by
     * @return order with corresponding id
     */
    OrderDto getOrderById(String id);
}
