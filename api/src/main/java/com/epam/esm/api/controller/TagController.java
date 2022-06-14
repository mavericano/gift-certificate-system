package com.epam.esm.api.controller;

import com.epam.esm.core.dto.TagDto;
import com.epam.esm.core.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getAllTags() {
        return tagService.getAllTags().stream().map(this::addLinksToTag).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable String id) {
        return addLinksToTag(tagService.getTagById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeTagById(@PathVariable String id) {
        tagService.removeTagById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody @Valid TagDto tag) {
        return tagService.addTag(tag);
    }

    private TagDto addLinksToTag(TagDto tag) {
        tag.add(linkTo(methodOn(TagController.class)
                .getTagById(String.valueOf(tag.getId()))).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class)
                .removeTagById(String.valueOf(tag.getId()))).withRel("delete"));
        tag.add(linkTo(GiftCertificateController.class).slash("search?tagName=" + tag.getName()).withRel("certificates"));
        return tag;
    }
}
