package com.epam.esm.api.controller;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
    public List<GiftCertificate> getAllGiftCertificates() {
        System.out.println("eto get zapros");
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate getGiftCertificateById(@PathVariable String id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGiftCertificateById(@PathVariable String id) {
        giftCertificateService.removeGiftCertificateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificate addGiftCertificate(@RequestBody GiftCertificate giftCertificate) {

        return null;
    }
}
