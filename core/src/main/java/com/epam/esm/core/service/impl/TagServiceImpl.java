package com.epam.esm.core.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public List<Tag> getAllTags() {
        return tagRepository.getAllTags();
    }

    @Override
    @Transactional
    public Tag getTagById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        return tagRepository.getTagById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No tag for id %d", longId)));
    }

    @Override
    @Transactional
    public void removeTagById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        tagRepository.removeTagById(longId);
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        return tagRepository.addTag(tag);
    }
}
