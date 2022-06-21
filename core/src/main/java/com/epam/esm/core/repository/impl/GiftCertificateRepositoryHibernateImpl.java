package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidSortParamsException;
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
    public List<GiftCertificate> getAllGiftCertificatesByRequirements(List<String> tagNames, String name, String description, String sortBy, String sortType, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);

        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
        if (description != null) predicates.add(criteriaBuilder.like(root.get("description"), "%"+description+"%"));
        if (tagNames != null) {
            List<Predicate> tagPredicates = new ArrayList<>();
            Subquery<Long> cr = criteriaQuery.subquery(Long.class);
            Root<GiftCertificate> tagNamesRoot = cr.from(GiftCertificate.class);
            Join<GiftCertificate, Tag> tagJoin = tagNamesRoot.join("tagSet", JoinType.LEFT);
            Predicate predicate;
            tagNames.forEach(tagName -> tagPredicates.add(
                    criteriaBuilder.equal(
                            criteriaBuilder.max(
                                    criteriaBuilder.selectCase().when(criteriaBuilder.equal(tagJoin.get("name"), tagName), 1).otherwise(0).as(Integer.class)
                            )
                    , 1)
            ));
            cr.select(tagNamesRoot.get("id"));
            cr.groupBy(tagNamesRoot.get("id"));
            cr.having(tagPredicates.toArray(new Predicate[0]));

            predicate = criteriaBuilder.in(root.get("id")).value(cr);
            predicates.add(predicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        if (sortType != null) {
            try {
                criteriaQuery.orderBy(sortType.equalsIgnoreCase("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
            } catch (IllegalArgumentException e) {
                throw new InvalidSortParamsException("sortByNotFoundExceptionMessage");
            }
        }

        criteriaQuery.distinct(true);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(criteriaQuery);
        int lastPageNumber = (int) Math.ceil((double)query.getResultList().size() / size);
        lastPageNumber = lastPageNumber == 0 ? 1 : lastPageNumber;
        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
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
    public List<GiftCertificate> getAllGiftCertificates(int page, int size, String sortBy, String sortType) {
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(certificate) from GiftCertificate certificate", Long.class);
        int lastPageNumber = (int) Math.ceil((double)countQuery.getSingleResult() / size);

        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");

        TypedQuery<GiftCertificate> query;
        if (sortBy != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
            Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
            criteriaQuery.select(root);
            try {
                criteriaQuery.orderBy(sortType.equalsIgnoreCase("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
            } catch (IllegalArgumentException e) {
                throw new InvalidSortParamsException("sortByNotFoundExceptionMessage");
            }
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery("from GiftCertificate", GiftCertificate.class);
        }
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        giftCertificate.setId(0);
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
