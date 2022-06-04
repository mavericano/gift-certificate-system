package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.DuplicateTagNameException;
import com.epam.esm.core.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryHibernateImpl implements TagRepository {
    //spring injects proxy
    final EntityManager entityManager;

    public TagRepositoryHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
    public List<Tag> getAllTags() {
        TypedQuery<Tag> query = entityManager.createQuery("from Tag", Tag.class);
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
