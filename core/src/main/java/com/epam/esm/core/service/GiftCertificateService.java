package com.epam.esm.core.service;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.GiftCertificate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificateDto> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto);

    List<GiftCertificateDto> getAllGiftCertificates();

    GiftCertificateDto getGiftCertificateById(String id);

    @Transactional
    void removeGiftCertificateById(String id);

    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto updateGiftCertificateFull(GiftCertificateDto giftCertificateDto);
}
