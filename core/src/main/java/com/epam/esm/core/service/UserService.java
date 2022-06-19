package com.epam.esm.core.service;


import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;

import java.util.List;
//TODO javadoc
public interface UserService {
    UserDto getUserById(String id);
    List<UserDto> getAllUsers(int page, int size, String sortBy, String sortType);
    List<OrderDto> getOrdersForUserById(String id, int page, int size, String sortBy, String sortType);
//    TagDto getTopTag();
}
