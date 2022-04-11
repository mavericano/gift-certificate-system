package com.epam.esm.api.controller;

import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gift-certificates")
public class GiftCertificateController {

    final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllGiftCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getGiftCertificateById(@PathVariable String id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGiftCertificateById(@PathVariable String id) {
        giftCertificateService.removeGiftCertificateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto addGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.addGiftCertificate(giftCertificateDto);
    }

    //TODO add PUT and PATCH
}
