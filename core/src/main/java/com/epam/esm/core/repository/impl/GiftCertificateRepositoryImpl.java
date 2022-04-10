package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.mapper.GiftCertificateRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificateById(long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM gift_certificate WHERE id = ?", new GiftCertificateRowMapper(), id));
    }

    //if db is empty, returns empty list
    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate WHERE id = 52", new GiftCertificateRowMapper());
    }

    //TODO not void
    @Override
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        jdbcTemplate.update("INSERT INTO gift_certificate VALUES (?,?,?,?,?,?)",
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate());
        return giftCertificate;
    }

    //TODO fix
    @Override
    public void removeGiftCertificateById(long id) {
        jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", id);
    }

    //TODO add update
}
