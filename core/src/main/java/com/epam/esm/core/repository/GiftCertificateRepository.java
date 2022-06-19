package com.epam.esm.core.repository;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateRepository {
    /**
     * Retrieves all certificates corresponding to search parameters
     * @param searchParamsDto parameters of the search
     * @return List of certificates corresponding to the parameters
     */
    //List<GiftCertificate> getAllGiftCertificatesByRequirements(SearchParamsDto searchParamsDto);
    List<GiftCertificate> getAllGiftCertificatesByRequirements(List<String> tagNames, String name, String description, String sortBy, String sortType, int page, int size);

    Set<Tag> getAllTagsForGiftCertificateById(long id);

    /**
     * Retrieves a certificate by id
     * @param id id to find by
     * @return Optional of certificate with corresponding id
     */
    Optional<GiftCertificate> getGiftCertificateById(long id);

    /**
     * Retrieves all certificates
     * @return List of all certificates
     */
    List<GiftCertificate> getAllGiftCertificates(int page, int size, String sortBy, String sortType);

    /**
     * Adds a certificate
     * @param giftCertificate certificate to add
     * @return certificate, identical to the one in the data source
     */
    GiftCertificate addGiftCertificate(GiftCertificate giftCertificate);

    /**
     * Adds entries to a linking table, linking tags to a certificate
     * @param giftCertificateId certificate id to add tags to
     * @param tagSet tags to add to a certificate
     */
    void linkTagsToGiftCertificate(long giftCertificateId, Set<Tag> tagSet);

    /**
     * Removes entries from a linking table, unlinking tags from a certificate
     * @param giftCertificateId certificate id to remove tags from
     * @param tagSet tags to remove from a certificate
     */
    void unlinkTagsFromGiftCertificate(long giftCertificateId, Set<Tag> tagSet);

    /**
     * Deletes a certificate by id
     * @param id id to delete by
     */
    void removeGiftCertificateById(long id);

    /**
     * Updates a certificate
     * @param giftCertificate certificate containing new values
     * @return certificate, identical to the one in the data source
     */
    GiftCertificate updateGiftCertificateFull(GiftCertificate giftCertificate);

    /**
     * Checks, whether there is a certificate with such id in data source
     * @param id id to check
     * @return true, if there is a certificate with such id, otherwise false
     */
    boolean existsGiftCertificateById(long id);
}
