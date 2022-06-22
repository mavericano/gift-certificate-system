package com.epam.esm.core.converter;

import com.epam.esm.core.dto.UserDto;
import com.epam.esm.core.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper/*(uses = OrderMapper.class)*/
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "createDate", source = "createDate")
    UserDto userToUserDto(User user);

    @Mapping(target = "createDate", source = "createDate")
    User userDtoToUser(UserDto userDto);
}
