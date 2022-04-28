package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.EntityDtoConverter;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.GiftCertificateService;

//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto) {
        if ((searchParamsDto.getSortBy() == null) ^ (searchParamsDto.getSortType() == null)) {
            throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        }
        return giftCertificateRepository.getAllGiftCertificatesByRequirements(searchParamsDto).stream().map((entity) ->
                entityDtoConverter.toDto(entity, giftCertificateRepository.getAllTagsForGiftCertificateById(entity.getId())))
                .collect(Collectors.toList());
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
        long longId = validateId(id);
        Set<Tag> tagSet = giftCertificateRepository.getAllTagsForGiftCertificateById(longId);
        return entityDtoConverter.toDto(giftCertificateRepository.getGiftCertificateById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No gift certificate for id %d", longId))),
                tagSet);
    }

    @Override
    @Transactional
    public void removeGiftCertificateById(String id) {
        long longId = validateId(id);
        giftCertificateRepository.removeGiftCertificateById(longId);
    }

    @Override
    @Transactional
    public GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto) {
        Set<Tag> tagSet = giftCertificateDto.getTagSet();
        GiftCertificate requestCertificate = entityDtoConverter.toEntity(giftCertificateDto);
        tagSet = tagRepository.fetchAndAddNewTags(tagSet);
        GiftCertificate addedCertificate = giftCertificateRepository.addGiftCertificate(requestCertificate);

        giftCertificateRepository.linkTagsToGiftCertificate(addedCertificate.getId(), tagSet);

        return entityDtoConverter.toDto(addedCertificate, giftCertificateRepository.
                getAllTagsForGiftCertificateById(addedCertificate.getId()));
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGiftCertificateFull(GiftCertificateDto giftCertificateDto) {
        long id = giftCertificateDto.getId();
        if (giftCertificateRepository.existsGiftCertificateById(id)) {
            GiftCertificate giftCertificate = entityDtoConverter.toEntity(giftCertificateDto);
            Set<Tag> tagSet = giftCertificateDto.getTagSet();

            giftCertificateRepository.unlinkTagsFromGiftCertificate(id, tagSet);
            tagSet = tagRepository.fetchAndAddNewTags(tagSet);
            giftCertificateRepository.linkTagsToGiftCertificate(id, tagSet);

            return entityDtoConverter.toDto(giftCertificateRepository.updateGiftCertificateFull(giftCertificate), tagSet);
        } else {
            throw new InvalidRecordException("updateInvalidRecordExceptionMessage");
        }
    }

//    @Override
//    @Transactional
//    public GiftCertificateDto updateGiftCertificatePartial(GiftCertificateDto giftCertificateDto) {
//        long id = giftCertificateDto.getId();
//        if (giftCertificateRepository.existsGiftCertificateById(id)) {
//            GiftCertificate giftCertificate = entityDtoConverter.toEntity(giftCertificateDto);
//            Set<Tag> tagSet = giftCertificateDto.getTagSet();
//
//            return null;
//        } else {
//            throw new InvalidRecordException(String.format("No GiftCertificate for id %d", id));
//        }
//    }

    private long validateId(String id) {
        if (StringUtils.isNumeric(id)) {
            return Long.parseLong(id);
        } else {
            throw new InvalidIdException();
        }
    }
}
