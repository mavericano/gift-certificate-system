package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.TagMapper;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Order;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.repository.UserRepository;
import com.epam.esm.core.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public TagServiceImpl(@Qualifier("tagRepositoryHibernateImpl") TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TagDto> getAllTags(int page, int size, String sortBy, String sortType) {
        if ((sortBy == null) ^ (sortType == null)) throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        if (page < 1 || size < 1) throw new InvalidPageSizeException("pageSizeLessThan1ExceptionMessage");

        List<Tag> tags = tagRepository.getAllTags(page, size, sortBy, sortType);
        return tags.stream().map(TagMapper.INSTANCE::tagToTagDto).collect(Collectors.toList());
    }

    @Override
    public TagDto getTagById(String id) {
        long longId = validateId(id);
        return TagMapper.INSTANCE.tagToTagDto(
                tagRepository.getTagById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No tag for id %d", longId)))
        );
    }

    @Override
    public void removeTagById(String id) {
        long longId = validateId(id);
        tagRepository.removeTagById(longId);
    }

    @Override
    public TagDto addTag(TagDto tag) {
        return TagMapper.INSTANCE.tagToTagDto(tagRepository.addTag(TagMapper.INSTANCE.tagDtoToTag(tag)));
    }

    @Override
    public TagDto getTopTag() {
        User user = userRepository.getMaxOrderSumUser();
        return TagMapper.INSTANCE.tagToTagDto(getTopTagForUser(user));
    }

    private Tag getTopTagForUser(User user) {
        Set<Tag> uniqueTags = new HashSet<>();
        Map<Tag, Integer> tagCounts = new HashMap<>();
        for (Order order : user.getOrders()) {
            List<GiftCertificate> certificates = order.getCertificates();
            for (GiftCertificate certificate : certificates) {
                Set<Tag> tags = certificate.getTagSet();
                for (Tag tag : tags) {
                    if (uniqueTags.add(tag)) {
                        //add to counts
                        tagCounts.put(tag, 1);
                    } else {
                        //increase counter
                        tagCounts.replace(tag, tagCounts.get(tag) + 1);
                    }
                }
            }
        }


        return tagCounts.entrySet().stream().
                sorted((entry1, entry2) -> entry2.getValue() - entry1.getValue()).
                collect(Collectors.toList()).get(0).getKey();
    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
