package com.epam.esm.core.converter.impl;

import com.epam.esm.core.converter.EntityDtoConverter;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
        giftCertificateDto.setTagSet(tagSet);
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto giftCertificateDto) {
        return modelMapper.map(giftCertificateDto, GiftCertificate.class);
    }
}
