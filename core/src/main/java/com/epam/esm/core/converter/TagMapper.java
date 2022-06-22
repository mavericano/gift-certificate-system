package com.epam.esm.core.converter;

import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
        TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

        @Mapping(target = "certificates", ignore = true)
        @Mapping(target = "createDate", source = "createDate")
        Tag tagDtoToTag(TagDto tagDto);

        @Mapping(target = "certificates", ignore = true)
        @Mapping(target = "createDate", source = "createDate")
        TagDto tagToTagDto(Tag tag);
}
