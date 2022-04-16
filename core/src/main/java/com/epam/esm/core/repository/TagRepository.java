package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository {
    Optional<Tag> getTagById(long id);

    Optional<Tag> getTagByName(String name);

    List<Tag> getAllTags();

    Tag addTag(Tag tag);

    void removeTagById(long id);

    Set<Tag> fetchAndAddNewTags(Set<Tag> tagSet);
}
