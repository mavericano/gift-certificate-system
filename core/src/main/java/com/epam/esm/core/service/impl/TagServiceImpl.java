package com.epam.esm.core.service.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
public class TagServiceImpl implements TagService {

    //final ApplicationContext applicationContext;

    final TagRepository tagRepository;

    public TagServiceImpl(@Qualifier("tagRepositoryHibernateImpl") TagRepository tagRepository) {
        this.tagRepository = tagRepository;
        //this.applicationContext = applicationContext;
    }

    @Override
    @Transactional
    public List<Tag> getAllTags() {
        List<Tag> tags = tagRepository.getAllTags();
        //System.out.println(tags);
        return tags;
    }

    @Override
    @Transactional
    public Tag getTagById(String id) {
        long longId = validateId(id);
        return tagRepository.getTagById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No tag for id %d", longId)));
    }

    @Override
    @Transactional
    public void removeTagById(String id) {
        long longId = validateId(id);
        tagRepository.removeTagById(longId);
    }

    @Override
    @Transactional
    public Tag addTag(Tag tag) {
        return tagRepository.addTag(tag);
    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
