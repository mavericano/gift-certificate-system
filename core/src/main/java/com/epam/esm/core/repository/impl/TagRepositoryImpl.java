package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.DuplicateTagNameException;
import com.epam.esm.core.repository.TagRepository;
import com.epam.esm.core.repository.mapper.TagRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tag> getTagById(long id) {
        try {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM tag WHERE id = ?", new TagRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> getTagByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM tag WHERE name = ?", new TagRowMapper(), name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query("SELECT * FROM tag", new TagRowMapper());
    }

    @Override
    public Tag addTag(Tag tag) {
        if (checkIfExistsByName(tag.getName())) {
            throw new DuplicateTagNameException(String.format("Tag with name %s already exists", tag.getName()));
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO tag(name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, tag.getName());
                return ps;
            }, keyHolder);
            if (keyHolder.getKey() != null) {
                tag.setId(keyHolder.getKey().longValue());
            } else {
                throw new RuntimeException("Unable to retrieve id for added GiftCertificate");
            }
        }
        return tag;
    }

    private boolean checkIfExistsByName(String name) {
        return jdbcTemplate.query("SELECT count(*) FROM tag WHERE name = ?", rs -> {
            rs.next();
            return rs.getInt(1);
        }, name) > 0;
    }

    @Override
    public void removeTagById(long id) {
        jdbcTemplate.update("DELETE FROM gift_certificate__tag WHERE tag_id = ?", id);
        jdbcTemplate.update("DELETE FROM tag WHERE id = ?", id);
    }

    @Override
    public Set<Tag> fetchAndAddNewTags(Set<Tag> tagSet) {
        return tagSet.stream().map(tag ->
                getTagByName(
                        tag.getName()).
                        orElseGet(() -> addTag(tag)))
                .collect(Collectors.toSet());
    }
}
