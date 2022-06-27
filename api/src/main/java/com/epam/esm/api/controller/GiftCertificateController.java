package com.epam.esm.api.controller;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/gift-certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    final GiftCertificateService giftCertificateService;

    @GetMapping(path = "/search", params = {"page", "size"})
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(@RequestParam(required = false) List<String> tagNames,
                                                                         @RequestParam(required = false) String sortBy,
                                                                         @RequestParam(required = false) String sortType,
                                                                         @RequestParam(required = false) String name,
                                                                         @RequestParam(required = false) String description,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {
        return giftCertificateService.getAllGiftCertificatesByRequirements(tagNames, name, description, sortBy, sortType, page, size).stream().map(this::addLinksToGiftCertificate).collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "size"})
    public List<GiftCertificateDto> getAllGiftCertificates(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortType) {
        return giftCertificateService.getAllGiftCertificates(page, size, sortBy, sortType).stream().map(this::addLinksToGiftCertificate).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable String id) {
        return addLinksToGiftCertificate(giftCertificateService.getGiftCertificateById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeGiftCertificateById(@PathVariable String id) {
        giftCertificateService.removeGiftCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return addLinksToGiftCertificate(giftCertificateService.addGiftCertificate(giftCertificateDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificateFull(@PathVariable String id,
                                                        @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return addLinksToGiftCertificate(giftCertificateService.updateGiftCertificateFull(id, giftCertificateDto));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificatePartially(@PathVariable String id,
                                                             @RequestBody(required = false) Map<String, Object> updates) {
        return addLinksToGiftCertificate(giftCertificateService.updateGiftCertificatePartially(id, updates));
    }

    private GiftCertificateDto addLinksToGiftCertificate(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                        .getGiftCertificateById(String.valueOf(giftCertificateDto.getId()))).withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                        .removeGiftCertificateById(String.valueOf(giftCertificateDto.getId()))).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                        .updateGiftCertificateFull(String.valueOf(giftCertificateDto.getId()), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                        .updateGiftCertificatePartially(String.valueOf(giftCertificateDto.getId()), null)).withRel("patch"));
        return giftCertificateDto;
    }
}
