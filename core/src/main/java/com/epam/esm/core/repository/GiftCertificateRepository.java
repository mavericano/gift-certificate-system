package com.epam.esm.core.repository;

import com.epam.esm.core.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository {

    Optional<GiftCertificate> getGiftCertificateById(long id);

    List<GiftCertificate> getAllGiftCertificates();

    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    void removeGiftCertificateById(long id);
}
