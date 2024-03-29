package com.epam.esm.core.service;


import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {
    /**
     * Returns user by id.
     * @param id id to find by
     * @return user with corresponding id
     */
    UserDto getUserById(String id);

    /**
     * Returns all users.
     * @param page page number
     * @param size page size
     * @param sortBy sort by field
     * @param sortType sort type
     * @return list of users
     */
    List<UserDto> getAllUsers(int page, int size, String sortBy, String sortType);

    List<UserDto> getUsersByRequirements(String username, int page, int size);

    /**
     * Returns all orders for a specified user.
     * @param id id of a user
     * @param page page number
     * @param size page size
     * @param sortBy sort by field
     * @param sortType sort type
     * @return list of orders
     */
    List<OrderDto> getOrdersForUserById(String id, int page, int size, String sortBy, String sortType);

    Optional<User> getUserByUsername(String username);

    UserDto addUser(UserDto userDto);
}
