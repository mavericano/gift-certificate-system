package com.epam.esm.api.controller;

import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag getTagById(@PathVariable String id) {
        return tagService.getTagById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTagById(@PathVariable String id) {
        tagService.removeTagById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }
}
