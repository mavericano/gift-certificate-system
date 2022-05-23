package com.epam.esm.core.service;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;

import java.util.List;

//TODO https://github.com/mjc-school/mentors-handsbook/tree/master/modules

public interface GiftCertificateService {
    /**
     * Retrieves all certificates corresponding to search parameters
     * @param searchParamsDto parameters of the search
     * @return List of certificates corresponding to the parameters
     */
    List<GiftCertificateDto> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto);

    /**
     * Retrieves all certificates
     * @return List of all certificates
     */
    List<GiftCertificateDto> getAllGiftCertificates();

    /**
     * Retrieves a certificate by id
     * @param id id to find by
     * @return Certificate with corresponding id
     */
    GiftCertificateDto getGiftCertificateById(String id);

    /**
     * Deletes a certificate by id
     * @param id id to delete by
     */
    void removeGiftCertificateById(String id);

    /**
     * Adds a certificate
     * @param giftCertificateDto certificate to add
     * @return certificate, identical to the one in the data source
     */
    GiftCertificateDto addGiftCertificate(GiftCertificateDto giftCertificateDto);

    /**
     * Updates a certificate
     * @param giftCertificateDto certificate containing new values
     * @return certificate, identical to the one in the data source
     */
    GiftCertificateDto updateGiftCertificateFull(GiftCertificateDto giftCertificateDto);
}
