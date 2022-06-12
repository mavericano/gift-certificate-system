package com.epam.esm.core.service;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.OrderRequestDto;

//TODO javadoc
public interface OrderService {
    OrderDto placeOrder(OrderRequestDto orderRequestDto);

    OrderDto getOrderById(String id);
}
