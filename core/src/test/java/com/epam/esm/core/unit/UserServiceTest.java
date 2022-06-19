package com.epam.esm.core.unit;

import com.epam.esm.core.converter.UserMapper;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
//TODO fix tests
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnUserIfExistsById() {
        User user = new User();
        UserDto userDto = new UserDto();
        long id = 1;
        user.setId(id);
        userDto.setId(id);

        when(userRepository.getUserById(id)).thenReturn(Optional.of(user));

        Assertions.assertEquals(userDto, userService.getUserById(String.valueOf(id)));

        verify(userRepository).getUserById(id);
    }

    @Test
    public void shouldThrowExceptionIfOrderNotExists() {
        long id = 1;
        when(userRepository.getUserById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRecordException.class, () -> userService.getUserById(String.valueOf(id)));

        verify(userRepository).getUserById(id);
    }

    @Test
    public void shouldThrowExceptionIfIdNotNumerical() {
        String id = "a";
        Assertions.assertThrows(InvalidIdException.class, () -> userService.getUserById(id));
    }

//    @Test
//    public void shouldReturnEmptyListIfRepositoryIsEmpty(){
//        when(userRepository.getAllUsers(1, 1)).thenReturn(new ArrayList<>());
//
//        Assertions.assertTrue(userService.getAllUsers(1, 1).isEmpty());
//
//        verify(userRepository).getAllUsers(1, 1);
//    }
//
//    @Test
//    public void shouldReturnNonEmptyListIfRepositoryIsNonEmpty() {
//        when(userRepository.getAllUsers(1, 2)).thenReturn(Arrays.asList(new User(), new User()));
//
//        Assertions.assertFalse(userService.getAllUsers(1, 2).isEmpty());
//
//        verify(userRepository).getAllUsers(1, 2);
//    }
//
//    @Test
//    public void shouldReturnEmptyListIfNoOrdersForUser() {
//        long id = 1;
//        User user = User.builder().id(id).orders(new ArrayList<>()).build();
//        when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
//
//        Assertions.assertTrue(userService.getOrdersForUserById(String.valueOf(id), 1, 1).isEmpty());
//
//        verify(userRepository).getUserById(id);
//    }
//
//    @Test
//    public void shouldReturnNonEmptyListIfPresentOrdersForUser() {
//        long id = 1;
//        User user = User.builder().id(id).orders(new ArrayList<>(Arrays.asList(new Order(), new Order()))).build();
//        when(userRepository.getUserById(id)).thenReturn(Optional.of(user));
//
//        Assertions.assertFalse(userService.getOrdersForUserById(String.valueOf(id), 1, 1).isEmpty());
//
//        verify(userRepository).getUserById(id);
//    }
//
//    @Test
//    public void shouldThrowExceptionIfNoUserForId() {
//        long id = 1;
//        when(userRepository.getUserById(id)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(NoSuchRecordException.class, () -> userService.getOrdersForUserById(String.valueOf(id), 1, 1));
//
//        verify(userRepository).getUserById(id);
//    }
}
