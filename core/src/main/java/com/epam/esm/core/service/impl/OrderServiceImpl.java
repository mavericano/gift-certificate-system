package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.OrderMapper;
import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.OrderRequestDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.OrderRepository;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    final UserRepository userRepository;
    final GiftCertificateRepository giftCertificateRepository;
    final OrderRepository orderRepository;

    public OrderServiceImpl(UserRepository userRepository, GiftCertificateRepository giftCertificateRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        User customer = userRepository.getUserById(orderRequestDto.getCustomerId()).orElseThrow(NoSuchRecordException::new);
        List<GiftCertificate> certificates = orderRequestDto.getCertificatesIds().stream().map(id ->
                giftCertificateRepository.getGiftCertificateById(id).orElseThrow(NoSuchRecordException::new)).collect(Collectors.toList());
        BigDecimal finalPrice = certificates.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = Order.builder().customer(customer).certificates(certificates).finalPrice(finalPrice).build();
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.addOrder(order));
    }

    @Override
    public OrderDto getOrderById(String id) {
        long longId = validateId(id);
        return OrderMapper.INSTANCE.orderToOrderDto(orderRepository.getOrderById(longId).orElseThrow(NoSuchRecordException::new));
    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
