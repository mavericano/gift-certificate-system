package com.epam.esm.core.repository;

import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagRepository {
    /**
     * Retrieves a tag by id
     * @param id id to find by
     * @return Optional of tag with corresponding id
     */
    Optional<Tag> getTagById(long id);

    /**
     * Retrieves a tag by name
     * @param name name to find by
     * @return Optional of tag with corresponding name
     */
    Optional<Tag> getTagByName(String name);

    /**
     * Retrieves all tags
     * @return List containing all tags
     */
    List<Tag> getAllTags(int page, int size, String sortBy, String sortType);

    /**
     * Adds a tag
     * @param tag tag to add
     * @return tag, identical to the one in the data source
     */
    Tag addTag(Tag tag);

    /**
     * Deletes a tag by id
     * @param id id to delete by
     */
    void removeTagById(long id);

    /**
     * Adds tags, which are not already present, to a data source
     * @param tagSet tags to add
     * @return Set of tags, both present and added
     */
    Set<Tag> fetchAndAddNewTags(Set<Tag> tagSet);
}
