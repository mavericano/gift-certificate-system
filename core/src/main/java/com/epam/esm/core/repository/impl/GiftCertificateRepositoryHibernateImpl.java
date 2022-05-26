package com.epam.esm.core.repository.impl;

import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateRepositoryHibernateImpl implements GiftCertificateRepository {

    private final EntityManager entityManager;

    public GiftCertificateRepositoryHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto) {
//        TODO Criteria API
        return null;
    }

    @Override
    public Set<Tag> getAllTagsForGiftCertificateById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return giftCertificate.getTagSet();
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificateById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        return Optional.ofNullable(giftCertificate);
    }

    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        TypedQuery<GiftCertificate> query = entityManager.createQuery("from GiftCertificate", GiftCertificate.class);
        return query.getResultList();
    }

    @Override
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public void linkTagsToGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {

    }

    @Override
    public void unlinkTagsFromGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {

    }

    @Override
    public void removeGiftCertificateById(long id) {

    }

    @Override
    public GiftCertificate updateGiftCertificateFull(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public boolean existsGiftCertificateById(long id) {
        return false;
    }
}
