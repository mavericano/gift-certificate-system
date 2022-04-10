package com.epam.esm.core.converter;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;

import java.util.Set;

public interface EntityDtoConverter {
    GiftCertificateDto toDto(GiftCertificate giftCertificate, Set<Tag> tagSet);

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);
}
