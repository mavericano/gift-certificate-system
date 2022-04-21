package com.epam.esm.core.repository;

import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateRepository {

    List<GiftCertificate> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto);

    Set<Tag> getAllTagsForGiftCertificateById(long id);

    Optional<GiftCertificate> getGiftCertificateById(long id);

    List<GiftCertificate> getAllGiftCertificates();

    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    void linkTagsToGiftCertificate(long giftCertificateId, Set<Tag> tagSet);

    void unlinkTagsFromGiftCertificate(long giftCertificateId, Set<Tag> tagSet);

    void removeGiftCertificateById(long id);

    //TODO add update
    GiftCertificate updateGiftCertificateFull(GiftCertificate giftCertificate);

    boolean existsGiftCertificateById(long id);
}
