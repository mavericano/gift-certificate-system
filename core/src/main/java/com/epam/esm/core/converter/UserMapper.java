package com.epam.esm.core.converter;

import com.epam.esm.core.dto.OrderDto;
import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper/*(uses = OrderMapper.class)*/
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//    @Mapping(target = "orders", ignore = true)
    UserDto userToUserDto(User user);

//    @Mapping(target = "orders", ignore = true)
    User userDtoToUser(UserDto userDto);

//    default OrderDto OrdertoOrderDto(Order order) {
//        OrderDto orderDto = new OrderDto();
//
//        orderDto.setOrderId(orderDto.getOrderId());
//        orderDto.
//
//        return orderDto;
//    }

}
