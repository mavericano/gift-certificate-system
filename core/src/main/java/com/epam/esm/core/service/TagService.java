package com.epam.esm.core.service;

import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.Tag;

import java.util.List;

public interface TagService {
    /**
     * Retrieves all tags
     * @return List containing all tags
     */
    List<TagDto> getAllTags(int page, int size, String sortBy, String sortType);

    /**
     * Retrieves a tag by id
     * @param id id to find by
     * @return Tag with corresponding id
     */
    TagDto getTagById(String id);

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
    TagDto addTag(TagDto tag);

    TagDto getTopTag();
}
