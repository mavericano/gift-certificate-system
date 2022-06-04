package com.epam.esm.core.converter.impl;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.entity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GiftCertificateMapper {

    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);

    GiftCertificateDto certificateToCertificateDto(GiftCertificate giftCertificate);
    GiftCertificate certificateDtoToCertificate(GiftCertificateDto giftCertificateDto);
}
