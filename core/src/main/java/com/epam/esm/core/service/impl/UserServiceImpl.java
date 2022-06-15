package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.OrderMapper;
import com.epam.esm.core.converter.UserMapper;
import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.UserService;
import javafx.scene.effect.SepiaTone;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
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
    public List<OrderDto> getOrdersForUserById(String id, int page, int size, String sortBy, String sortType) {
        long longId = validateId(id);
        if ((sortBy == null) ^ (sortType == null)) throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        if (page < 1 || size < 1) throw new InvalidPageSizeException("pageSizeLessThan1ExceptionMessage");

        List<Order> orders = userRepository.getUserById(longId).orElseThrow(NoSuchRecordException::new).getOrders();
        int lastPageNumber = (int) Math.ceil((double)orders.size() / size);
        lastPageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        return orders.stream().sorted((order1, order2) -> {
            int ret = 0;
            if ("orderId".equals(sortBy)) {
                ret = Long.compare(order1.getOrderId(), order2.getOrderId());
            } else if ("finalPrice".equals(sortBy)) {
                ret = order1.getFinalPrice().compareTo(order2.getFinalPrice());
            } else if ("purchaseTime".equals(sortBy)) {
                ret =  order1.getPurchaseTime().compareTo(order2.getPurchaseTime());
            }
            if ("desc".equalsIgnoreCase(sortType)) {
                ret *= -1;
            }
            return ret;
        }).skip((long) (page - 1) * size).limit(size).map(OrderMapper.INSTANCE::orderToOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsers(int page, int size, String sortBy, String sortType) {
        if ((sortBy == null) ^ (sortType == null)) throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        if (page < 1 || size < 1) throw new InvalidPageSizeException("pageSizeLessThan1ExceptionMessage");

        return userRepository.getAllUsers(page, size, sortBy, sortType).stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) {
        long longId = validateId(id);
        return UserMapper.INSTANCE.userToUserDto(userRepository.getUserById(longId).orElseThrow(NoSuchRecordException::new));
    }

    @Override
    public TagDto getTopTag() {
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
