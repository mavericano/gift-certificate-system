package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.OrderMapper;
import com.epam.esm.core.converter.UserMapper;
import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDto> getOrdersForUserById(String id) {
        long longId = validateId(id);
        return userRepository.getUserById(longId).orElseThrow(NoSuchRecordException::new)
                .getOrders().stream().map(OrderMapper.INSTANCE::orderToOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) {
        long longId = validateId(id);
        return UserMapper.INSTANCE.userToUserDto(userRepository.getUserById(longId).orElseThrow(NoSuchRecordException::new));
    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
