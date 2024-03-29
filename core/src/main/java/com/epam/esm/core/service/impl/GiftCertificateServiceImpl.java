package com.epam.esm.core.service.impl;

import com.epam.esm.core.converter.GiftCertificateMapper;
import com.epam.esm.core.converter.TagMapper;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidIdException;
import com.epam.esm.core.exception.InvalidPageSizeException;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.service.GiftCertificateService;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    final GiftCertificateRepository giftCertificateRepository;
    final TagRepository tagRepository;

    @Override
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(List<String> tagNames, String name, String description, String sortBy, String sortType, int page, int size) {
        if ((sortBy == null) ^ (sortType == null)) throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        if (page < 1 || size < 1) throw new InvalidPageSizeException("pageSizeLessThan1ExceptionMessage");
        return giftCertificateRepository.getAllGiftCertificatesByRequirements(tagNames, name, description, sortBy, sortType, page, size).stream().map(GiftCertificateMapper.INSTANCE::certificateToCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getAllGiftCertificates(int page, int size, String sortBy, String sortType) {
        if ((sortBy == null) ^ (sortType == null)) throw new InvalidRecordException("searchInvalidRecordExceptionMessage");
        if (page < 1 || size < 1) throw new InvalidPageSizeException("pageSizeLessThan1ExceptionMessage");
        return giftCertificateRepository.getAllGiftCertificates(page, size, sortBy, sortType).stream().map(GiftCertificateMapper.INSTANCE::certificateToCertificateDto)
                .collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getGiftCertificateById(String id) {
        long longId = validateId(id);
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

        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(addedCertificate);
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
            throw new InvalidRecordException("updateInvalidRecordExceptionMessage");
        }
        long longId = validateId(id);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.getGiftCertificateById(longId);
        if (giftCertificate.isPresent()) {
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(GiftCertificate.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    if ("price".equals(key)) {
                        value = (value instanceof Integer) ? BigDecimal.valueOf((int) value) : BigDecimal.valueOf((double) value);
                    }
                    ReflectionUtils.setField(field, giftCertificate.get(), value);
                }
            });
            return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificateRepository.updateGiftCertificateFull(giftCertificate.get()));
        } else {
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
