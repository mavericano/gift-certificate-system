package com.epam.esm.core.repository;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateRepository {

    Set<Tag> getAllTagsForGiftCertificateById(long id);

    Optional<GiftCertificate> getGiftCertificateById(long id);

    List<GiftCertificate> getAllGiftCertificates();

    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    void removeGiftCertificateById(long id);
}
