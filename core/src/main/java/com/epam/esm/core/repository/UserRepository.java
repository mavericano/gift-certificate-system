package com.epam.esm.core.repository;

import com.epam.esm.core.entity.User;

import java.util.List;
import java.util.Optional;
public interface UserRepository {
    /**
     * Returns user by id.
     * @param id id to find by
     * @return user with corresponding id
     */
    Optional<User> getUserById(long id);

    /**
     * Returns all users.
     * @param page page number
     * @param size page size
     * @param sortBy sort by field
     * @param sortType sort type
     * @return list of users
     */
    List<User> getAllUsers(int page, int size, String sortBy, String sortType);

    /**
     * Returns a user with max sum of all orders.
     * @return user with max sum of all orders
     */
    User getMaxOrderSumUser();
}
