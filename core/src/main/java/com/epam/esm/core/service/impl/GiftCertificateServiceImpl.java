package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.EntityDtoConverter;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@EnableTransactionManagement
public class GiftCertificateServiceImpl implements GiftCertificateService {

    final TagRepository tagRepository;
    final GiftCertificateRepository giftCertificateRepository;
    final EntityDtoConverter entityDtoConverter;

    public GiftCertificateServiceImpl(TagRepository tagRepository, GiftCertificateRepository giftCertificateRepository, EntityDtoConverter entityDtoConverter) {
        this.tagRepository = tagRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> getAllGiftCertificates() {
        return giftCertificateRepository.getAllGiftCertificates().stream().map((entity) ->
                entityDtoConverter.toDto(entity, giftCertificateRepository.getAllTagsForGiftCertificateById(entity.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto getGiftCertificateById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        Set<Tag> tagSet = giftCertificateRepository.getAllTagsForGiftCertificateById(longId);
        return entityDtoConverter.toDto(giftCertificateRepository.getGiftCertificateById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No gift certificate for id %d", longId))),
                tagSet);
    }

    @Override
    @Transactional
    public void removeGiftCertificateById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        giftCertificateRepository.removeGiftCertificateById(longId);
    }

    @Override
    @Transactional
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        //TODO validate
        Set<Tag> tagSet = giftCertificateDto.getTagSet();
        GiftCertificate requestCertificate = entityDtoConverter.toEntity(giftCertificateDto);
        Set<Tag> newlyAdded = tagRepository.fetchAndAddNewTags(tagSet);
        GiftCertificate addedCertificate = giftCertificateRepository.addGiftCertificate(requestCertificate);
        giftCertificateRepository.linkTagsToGiftCertificate(addedCertificate.getId(), tagSet);

        return entityDtoConverter.toDto(addedCertificate, giftCertificateRepository.
                getAllTagsForGiftCertificateById(addedCertificate.getId()));
    }
}
