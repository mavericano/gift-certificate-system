package com.epam.esm.core.service;

import com.epam.esm.core.entity.GiftCertificate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GiftCertificateService {
    List<GiftCertificate> getAllGiftCertificates();

    GiftCertificate getGiftCertificateById(String id);

    @Transactional
    void removeGiftCertificateById(String id);
}
