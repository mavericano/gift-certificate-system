package com.epam.esm.core.unit;

import com.epam.esm.core.converter.OrderMapper;
import com.epam.esm.core.dto.OrderRequestDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.OrderRepository;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void shouldThrowExceptionIfUserNotExists() {
        long id = 1;
        OrderRequestDto orderRequestDto = OrderRequestDto.builder().customerId(id).certificatesIds(new ArrayList<>()).build();
        when(userRepository.getUserById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> orderService.placeOrder(orderRequestDto));

        verify(userRepository).getUserById(id);
    }

    @Test
    public void shouldThrowExceptionIfCertificateNotExists() {
        long id = 1;
        OrderRequestDto orderRequestDto = OrderRequestDto.builder().customerId(id).certificatesIds(new ArrayList<>()).build();
        orderRequestDto.getCertificatesIds().add(1L);
        when(userRepository.getUserById(id)).thenReturn(Optional.of(new User()));
        when(giftCertificateRepository.getGiftCertificateById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> orderService.placeOrder(orderRequestDto));

        verify(userRepository).getUserById(id);
    }

    @Test
    public void shouldAddOrder() {
        long customerId = 1;
        long certificateId = 1;
        User user = User.builder().id(customerId).build();
        GiftCertificate giftCertificate = GiftCertificate.builder().id(certificateId).price(BigDecimal.valueOf(50)).build();
        List<GiftCertificate> certificates = new ArrayList<>();
        certificates.add(giftCertificate);

        OrderRequestDto orderRequestDto = OrderRequestDto.builder().customerId(customerId).certificatesIds(Collections.singletonList(certificateId)).build();

        when(userRepository.getUserById(customerId)).thenReturn(Optional.of(user));
        when(giftCertificateRepository.getGiftCertificateById(certificateId)).thenReturn(Optional.of(giftCertificate));
        Order order = Order.builder().customer(user).certificates(certificates).finalPrice(BigDecimal.valueOf(50)).build();
        when(orderRepository.addOrder(order)).thenReturn(order);

        Assertions.assertEquals(OrderMapper.INSTANCE.orderToOrderDto(order), orderService.placeOrder(orderRequestDto));

        verify(userRepository).getUserById(customerId);
        verify(giftCertificateRepository).getGiftCertificateById(certificateId);
        verify(orderRepository).addOrder(order);
    }

    @Test
    public void shouldThrowExceptionIfOrderNotExists() {
        long id = 1;
        when(orderRepository.getOrderById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> orderService.getOrderById(String.valueOf(id)));

        verify(orderRepository).getOrderById(id);
    }

    @Test
    public void shouldThrowExceptionIfIdNotNumerical() {
        String id = "a";
        Assertions.assertThrows(InvalidIdException.class, () -> orderService.getOrderById(id));
    }

    @Test
    public void shouldFindOrder() {
        long id = 1;
        Order order = Order.builder().orderId(id).build();
        when(orderRepository.getOrderById(id)).thenReturn(Optional.of(order));

        Assertions.assertEquals(OrderMapper.INSTANCE.orderToOrderDto(order), orderService.getOrderById(String.valueOf(id)));

        verify(orderRepository).getOrderById(id);
    }
}
