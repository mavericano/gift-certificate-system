package com.epam.esm.core.repository.impl;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.repository.mapper.TagRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Tag getTagById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tag WHERE id = ?", new TagRowMapper(), id);
    }

    public List<Tag> getAllTags() {
        return jdbcTemplate.query("SELECT * FROM tag", new TagRowMapper());
    }

    public void addTag(Tag tag) {
        jdbcTemplate.update("INSERT INTO tag VALUES ?", tag.getName());
    }

    public void removeTagById(long id) {
        jdbcTemplate.update("DELETE FROM tag WHERE id = ?", id);
    }
}
