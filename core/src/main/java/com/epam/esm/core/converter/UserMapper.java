package com.epam.esm.core.converter;

import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper/*(uses = OrderMapper.class)*/
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
