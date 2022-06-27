package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.DuplicateTagNameException;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidSortParamsException;
import com.epam.esm.core.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TagRepositoryHibernateImpl implements TagRepository {
    //spring injects proxy
    final EntityManager entityManager;

    @Override
    public Optional<Tag> getTagById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        TypedQuery<Tag> query = entityManager.createQuery("select tag from Tag tag where tag.name=:name", Tag.class);
        query.setParameter("name", name);
        Tag tag;
        try {
            tag = query.getSingleResult();
        } catch (NoResultException e) {
            tag = null;
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> getAllTags(int page, int size, String sortBy, String sortType) {
        TypedQuery<Long> countQuery = entityManager.createQuery("select count(tag) from Tag tag", Long.class);
        int lastPageNumber = (int) Math.ceil((double)countQuery.getSingleResult() / size);

        if (page > lastPageNumber) throw new InvalidPageSizeException("pageNumberTooBigExceptionMessage");
        TypedQuery<Tag> query;
        if (sortType != null) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
            Root<Tag> root = criteriaQuery.from(Tag.class);
            criteriaQuery.select(root);
            try {
                criteriaQuery.orderBy(sortType.equalsIgnoreCase("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
            } catch (IllegalArgumentException e) {
                throw new InvalidSortParamsException("sortByNotFoundExceptionMessage");
            }
            query = entityManager.createQuery(criteriaQuery);
        } else {
            query = entityManager.createQuery("from Tag", Tag.class);
        }
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Tag addTag(Tag tag) {
        if (getTagByName(tag.getName()).isPresent()) {
            throw new DuplicateTagNameException();
        } else {
            tag.setId(0);
            entityManager.persist(tag);
            return tag;
        }
    }

    @Override
    public void removeTagById(long id) {
        Query query = entityManager.createQuery("delete from Tag where id=:tagId");
        query.setParameter("tagId", id);
        query.executeUpdate();
    }

    @Override
    public Set<Tag> fetchAndAddNewTags(Set<Tag> tagSet) {
        return tagSet.stream().map(tag ->
                        getTagByName(
                                tag.getName()).
                                orElseGet(() -> addTag(tag)))
                .collect(Collectors.toSet());
    }
}
