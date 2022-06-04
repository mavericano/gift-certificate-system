package com.epam.esm.api.controller;

import com.epam.esm.api.exceptionhandler.BindingResultParser;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gift-certificates")
public class GiftCertificateController {

    final GiftCertificateService giftCertificateService;
    final BindingResultParser bindingResultParser;

    public GiftCertificateController(GiftCertificateService giftCertificateService, BindingResultParser bindingResultParser) {
        this.giftCertificateService = giftCertificateService;
        this.bindingResultParser = bindingResultParser;
    }

    @GetMapping("/search")
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(@RequestParam(required = false) String tagName,
                                                                         @RequestParam(required = false) String sortBy,
                                                                         @RequestParam(required = false) String sortType,
                                                                         @RequestParam(required = false) String name,
                                                                         @RequestParam(required = false) String description) {
        return giftCertificateService.getAllGiftCertificatesByRequirements(tagName, name, description, sortBy, sortType);
    }

    @GetMapping()
    public List<GiftCertificateDto> getAllGiftCertificates() {
        return giftCertificateService.getAllGiftCertificates();
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getGiftCertificateById(@PathVariable String id) {
        return giftCertificateService.getGiftCertificateById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGiftCertificateById(@PathVariable String id) {
        giftCertificateService.removeGiftCertificateById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto addGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.addGiftCertificate(giftCertificateDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificateFull(@PathVariable String id,
                                                        @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateGiftCertificateFull(id, giftCertificateDto);
    }
    //TODO implement
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificatePartially(@PathVariable String id,
                                                             @RequestBody Map<String, Object> updates) {
        return giftCertificateService.updateGiftCertificatePartially(id, updates);
    }
}
