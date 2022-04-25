package com.epam.esm.core.service;

import com.epam.esm.core.entity.Tag;

import java.util.List;

public interface TagService {
    /**
     * Retrieves all tags
     * @return List containing all tags
     */
    List<Tag> getAllTags();

    /**
     * Retrieves a tag by id
     * @param id id to find by
     * @return Tag with corresponding id
     */
    Tag getTagById(String id);

    /**
     * Deletes a tag by id
     * @param id id to delete by
     */
    void removeTagById(String id);

    /**
     * Adds a tag
     * @param tag tag to add
     * @return tag, identical to the one in the data source
     */
    Tag addTag(Tag tag);
}
