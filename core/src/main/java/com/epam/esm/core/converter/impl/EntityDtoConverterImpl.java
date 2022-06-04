package com.epam.esm.core.converter.impl;

import com.epam.esm.core.converter.EntityDtoConverter;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class EntityDtoConverterImpl implements EntityDtoConverter {

    private final ModelMapper modelMapper;

    public EntityDtoConverterImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public GiftCertificateDto toDto(GiftCertificate giftCertificate, Set<Tag> tagSet) {
        GiftCertificateDto giftCertificateDto = modelMapper.map(giftCertificate, GiftCertificateDto.class);
//        giftCertificateDto.setTagSet(tagSet);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDto, GiftCertificate.class);
//        giftCertificate.setCreateDate(LocalDateTime.now());
//        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return giftCertificate;
    }
}
