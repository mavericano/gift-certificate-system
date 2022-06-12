package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.OrderMapper;
import com.epam.esm.core.converter.UserMapper;
import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.UserService;
import javafx.scene.effect.SepiaTone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
        return userRepository.getUserById(longId).orElseThrow(() ->
//                TODO add exception message i18n
                new NoSuchRecordException(String.format("No user for id %d", longId)))
                .getOrders().stream().map(OrderMapper.INSTANCE::orderToOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) {
        long longId = validateId(id);
        return UserMapper.INSTANCE.userToUserDto(userRepository.getUserById(longId).orElseThrow(() ->
//                TODO add exception message i18n
                        new NoSuchRecordException(String.format("No user for id %d", longId))));
    }

    @Override
    public TagDto getTopTagForUserById(String id) {
        long longId = validateId(id);

        User user = userRepository.getUserById(longId).orElseThrow(() ->
//                TODO add exception message i18n
                new NoSuchRecordException(String.format("No user for id %d", longId)));
        List<Order> orders = user.getOrders();
        for (Order order : orders) {
            List<GiftCertificate> certificates = order.getCertificates();
            for (GiftCertificate certificate : certificates) {
                Set<Tag> tags = certificate.getTagSet();

            }
        }
        return null;
    }

    //Get the most widely used tag of a user with the highest cost of all orders

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
