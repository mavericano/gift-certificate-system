package com.epam.esm.core.service;

import com.epam.esm.core.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();
    Tag getTagById(String id);
    void removeTagById(String id);

    Tag addTag(Tag tag);
}
