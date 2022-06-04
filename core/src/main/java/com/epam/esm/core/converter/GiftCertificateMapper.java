package com.epam.esm.core.converter;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.entity.GiftCertificate;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TagMapper.class})
public interface GiftCertificateMapper {

    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    //@Mapping(target = "tagSet", ignore = true)
    GiftCertificateDto certificateToCertificateDto(GiftCertificate giftCertificate);

    //@Mapping(target = "tagSet", ignore = true)
    GiftCertificate certificateDtoToCertificate(GiftCertificateDto giftCertificateDto);

//    @AfterMapping
//    default void mapTags(GiftCertificate giftCertificate, GiftCertificateDto giftCertificateDto) {
//        giftCertificate.getTagSet().forEach(tag -> giftCertificateDto.getTagSet().add(TagMapper.INSTANCE.tagToTagDto(tag)));
//    }
}
