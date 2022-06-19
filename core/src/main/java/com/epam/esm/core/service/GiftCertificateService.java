package com.epam.esm.core.service;

import com.epam.esm.core.dto.GiftCertificateDto;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    /**
     * Retrieves all certificates corresponding to search parameters
     * @param tagName name of the tag
     * @param name part of the name of the certificate
     * @param description part of the description of the certificate
     * @param sortBy field to sort by
     * @param sortType type of sorting
     * @return List of certificates corresponding to the parameters
     */
    List<GiftCertificateDto> getAllGiftCertificatesByRequirements(List<String> tagNames, String name, String description, String sortBy, String sortType, int page, int size);

    /**
     * Retrieves all certificates
     * @return List of all certificates
     */
    List<GiftCertificateDto> getAllGiftCertificates(int page, int size, String sortBy, String sortType);

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
     * @param id id of a certificate to update
     * @param giftCertificateDto certificate containing new values
     * @return certificate, identical to the one in the data source
     */
    GiftCertificateDto updateGiftCertificateFull(String id, GiftCertificateDto giftCertificateDto);

    /**
     * Updates a certificate
     * @param id id of a certificate to update
     * @param updates map containing pairs <fieldName, newValue>
     * @return certificate, identical to the one in the data source
     */
    GiftCertificateDto updateGiftCertificatePartially(String id, Map<String, Object> updates);
}
