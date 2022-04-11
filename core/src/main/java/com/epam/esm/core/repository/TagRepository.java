package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Tag;

import java.util.Optional;
import java.util.Set;

public interface TagRepository {
    Optional<Tag> getTagById(long id);

    Set<Tag> fetchAndAddNewTags(Set<Tag> tagSet);
}
