package com.epam.esm.core.service;


import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;

import java.util.List;

public interface UserService {
    UserDto getUserById(String id);
    List<UserDto> getAllUsers();
    List<OrderDto> getOrdersForUserById(String id);

    TagDto getTopTagForUserById(String id);
}
