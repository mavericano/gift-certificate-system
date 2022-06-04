package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.repository.GiftCertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<GiftCertificate> getAllGiftCertificatesByRequirements(String tagName, String name, String description, String sortBy, String sortType) {
//        TODO Criteria API
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> tagJoin = root.join("tagSet");
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        if (description != null) predicates.add(criteriaBuilder.like(root.get("description"), "%"+description+"%"));
        if (tagName != null) predicates.add(criteriaBuilder.equal(tagJoin.get("name"), tagName));
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        if (sortType != null) {
            if (sortType.equalsIgnoreCase("ASC")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(sortBy)));
            }
        }

        criteriaQuery.distinct(true);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Set<Tag> getAllTagsForGiftCertificateById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        System.out.println(giftCertificate);
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
        giftCertificate.setId(0);
        //giftCertificate.getTagSet().forEach(tag -> tag.setId(0));
        LocalDateTime now = LocalDateTime.now();
        giftCertificate.setCreateDate(now);
        giftCertificate.setLastUpdateDate(now);
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void linkTagsToGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, giftCertificateId);
        giftCertificate.setTagSet(tagSet);
        entityManager.merge(giftCertificate);
    }

    @Override
    public void unlinkTagsFromGiftCertificate(long giftCertificateId, Set<Tag> tagSet) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, giftCertificateId);
        giftCertificate.getTagSet().removeAll(tagSet);
        entityManager.merge(giftCertificate);
    }

    @Override
    public void removeGiftCertificateById(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            giftCertificate.getTagSet().forEach(tag -> tag.getCertificates().remove(giftCertificate));
            entityManager.remove(giftCertificate);
        }
    }

    @Override
    public GiftCertificate updateGiftCertificateFull(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public boolean existsGiftCertificateById(long id) {
        return entityManager.find(GiftCertificate.class, id) != null;
    }
}
