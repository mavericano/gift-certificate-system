package com.epam.esm.core.service.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.exception.NoSuchRecordException;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    final GiftCertificateRepository giftCertificateRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    @Transactional
    public List<GiftCertificate> getAllGiftCertificates() {
        return giftCertificateRepository.getAllGiftCertificates();
    }

    @Override
    @Transactional
    public GiftCertificate getGiftCertificateById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        return giftCertificateRepository.getGiftCertificateById(longId).orElseThrow(() ->
                new NoSuchRecordException(String.format("No gift certificate for id %d", longId)));
    }

    @Override
    @Transactional
    public void removeGiftCertificateById(String id) {
        //TODO validate
        long longId = Long.parseLong(id);
        giftCertificateRepository.removeGiftCertificateById(longId);
    }
}
