package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Order addOrder(Order order);

    Optional<Order> getOrderById(long id);
}
