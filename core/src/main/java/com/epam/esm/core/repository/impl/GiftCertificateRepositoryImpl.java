package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.repository.GiftCertificateRepository;
import com.epam.esm.core.repository.mapper.GiftCertificateRowMapper;
import com.epam.esm.core.repository.mapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //if no tags for a certificate, produces empty set
    @Override
    public Set<Tag> getAllTagsForGiftCertificateById(long id) {
        return jdbcTemplate.queryForStream(
                "SELECT tag.id, tag.name FROM gift_certificate__tag JOIN tag ON tag_id = tag.id WHERE certificate_id = ?",
                new TagRowMapper(), id).collect(Collectors.toSet());
    }

    @Override
    public Optional<GiftCertificate> getGiftCertificateById(long id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM gift_certificate WHERE id = ?", new GiftCertificateRowMapper(), id));
    }

    //if db is empty, produces empty list
    @Override
    public List<GiftCertificate> getAllGiftCertificates() {
        return jdbcTemplate.query("SELECT * FROM gift_certificate", new GiftCertificateRowMapper());
    }

    @Override
    public GiftCertificate addGiftCertificate(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps =
                    con.prepareStatement(
                            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) VALUES (?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, giftCertificate.getName());
            ps.setString(2, giftCertificate.getDescription());
            ps.setDouble(3, giftCertificate.getPrice());
            ps.setInt(4, giftCertificate.getDuration());
            ps.setDate(5, new java.sql.Date(giftCertificate.getCreateDate().getTime()));
            ps.setDate(6, new java.sql.Date(giftCertificate.getLastUpdateDate().getTime()));
            return ps;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            giftCertificate.setId(keyHolder.getKey().longValue());
        } else {
            //TODO consider changing
            throw new RuntimeException("Unable to retrieve id for added GiftCertificate");
        }
        return giftCertificate;
    }

    //TODO fix
    @Override
    public void removeGiftCertificateById(long id) {
        jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", id);
    }

    //TODO add update
}
