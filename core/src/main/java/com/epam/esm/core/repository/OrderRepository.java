package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Order;

public interface OrderRepository {
    Order addOrder(Order order);
}
