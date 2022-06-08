package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.GiftCertificateMapper;
import com.epam.esm.core.converter.TagMapper;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.GiftCertificateService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {

    final GiftCertificateRepository giftCertificateRepository;
    final TagRepository tagRepository;
//    final EntityDtoConverter entityDtoConverter;

    public GiftCertificateServiceImpl(@Qualifier("tagRepositoryHibernateImpl") TagRepository tagRepository,
                                      @Qualifier("giftCertificateRepositoryHibernateImpl") GiftCertificateRepository giftCertificateRepository
//                                    ,  EntityDtoConverter entityDtoConverter
    ) {
        this.tagRepository = tagRepository;
        this.giftCertificateRepository = giftCertificateRepository;
//        this.entityDtoConverter = entityDtoConverter;
    }

    @Override
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(String tagName, String name, String description, String sortBy, String sortType) {
        if ((sortBy == null) ^ (sortType == null)) {
            throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        }
        return giftCertificateRepository.getAllGiftCertificatesByRequirements(tagName, name, description, sortBy, sortType).stream().map(GiftCertificateMapper.INSTANCE::certificateToCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getAllGiftCertificates() {
        return giftCertificateRepository.getAllGiftCertificates().stream().map(GiftCertificateMapper.INSTANCE::certificateToCertificateDto
                //entityDtoConverter.toDto(entity, giftCertificateRepository.getAllTagsForGiftCertificateById(entity.getId()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getGiftCertificateById(String id) {
        long longId = validateId(id);
//        Set<Tag> tagSet = giftCertificateRepository.getAllTagsForGiftCertificateById(longId);
        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificateRepository.getGiftCertificateById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No gift certificate for id %d", longId))));
    }

    @Override
    public void removeGiftCertificateById(String id) {
        long longId = validateId(id);
        giftCertificateRepository.removeGiftCertificateById(longId);
    }

    @Override
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        Set<TagDto> tagDtoSet = giftCertificateDto.getTagSet();
        Set<Tag> tagSet;
        if (tagDtoSet != null) {
            tagSet = tagDtoSet.stream().map(TagMapper.INSTANCE::tagDtoToTag).collect(Collectors.toSet());
        } else {
            tagSet = new HashSet<>();
        }
        GiftCertificate requestCertificate = GiftCertificateMapper.INSTANCE.certificateDtoToCertificate(giftCertificateDto);
        tagSet = tagRepository.fetchAndAddNewTags(tagSet);
        requestCertificate.setTagSet(tagSet);
        GiftCertificate addedCertificate = giftCertificateRepository.addGiftCertificate(requestCertificate);

        //giftCertificateRepository.linkTagsToGiftCertificate(addedCertificate.getId(), tagSet);

        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(addedCertificate);
//                entityDtoConverter.toDto(addedCertificate, giftCertificateRepository.
//                getAllTagsForGiftCertificateById(addedCertificate.getId()));
    }

    @Override
    public GiftCertificateDto updateGiftCertificateFull(String id, GiftCertificateDto giftCertificateDto) {
        long longId = validateId(id);
        if (giftCertificateRepository.existsGiftCertificateById(longId)) {
            GiftCertificate giftCertificate = GiftCertificateMapper.INSTANCE.certificateDtoToCertificate(giftCertificateDto);
            giftCertificate.setId(longId);
            Set<TagDto> tagDtoSet = giftCertificateDto.getTagSet();
            Set<Tag> tagSet;
            if (tagDtoSet != null) {
                tagSet = tagDtoSet.stream().map(TagMapper.INSTANCE::tagDtoToTag).collect(Collectors.toSet());
            } else {
                tagSet = new HashSet<>();
            }

            giftCertificateRepository.unlinkTagsFromGiftCertificate(longId, tagSet);
            tagSet = tagRepository.fetchAndAddNewTags(tagSet);
            giftCertificateRepository.linkTagsToGiftCertificate(longId, tagSet);

            return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificateRepository.updateGiftCertificateFull(giftCertificate));
        } else {
            throw new InvalidRecordException("updateInvalidRecordExceptionMessage");
        }
    }

    @Override
    public GiftCertificateDto updateGiftCertificatePartially(String id, Map<String, Object> updates) {
        if (updates == null) {
            //TODO add msg
            throw new InvalidRecordException("updateInvalidRecordExceptionMessage");
        }
        long longId = validateId(id);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.getGiftCertificateById(longId);
        if (giftCertificate.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(GiftCertificate.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, giftCertificate.get(), value);
                } else {
//                    TODO add
                    throw new RuntimeException("(totally a debug msg) field null in reflection in patch");
                }
            });
            return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificateRepository.updateGiftCertificateFull(giftCertificate.get()));
        } else {
//            TODO fix msg
            throw new InvalidRecordException("updateInvalidRecordExceptionMessage");
        }
    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
