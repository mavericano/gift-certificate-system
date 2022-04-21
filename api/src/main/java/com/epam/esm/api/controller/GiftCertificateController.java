package com.epam.esm.api.controller;

import com.epam.esm.api.exceptionhandler.BindingResultParser;
import com.epam.esm.core.dto.GiftCertificateDto;
import com.epam.esm.core.dto.SearchParamsDto;
import com.epam.esm.core.entity.Tag;
import com.epam.esm.core.exception.InvalidRecordException;
import com.epam.esm.core.service.GiftCertificateService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/gift-certificates")
public class GiftCertificateController {

    final GiftCertificateService giftCertificateService;
    final BindingResultParser bindingResultParser;

    public GiftCertificateController(GiftCertificateService giftCertificateService, BindingResultParser bindingResultParser) {
        this.giftCertificateService = giftCertificateService;
        this.bindingResultParser = bindingResultParser;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> getAllGiftCertificatesByRequirements(@RequestBody @Valid SearchParamsDto searchParamsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRecordException("Fields of SearchParams has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult));
        } else {
            return giftCertificateService.getAllGiftCertificatesByRequirements(searchParamsDto);
        }
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
    public GiftCertificateDto addGiftCertificate(@RequestBody @Valid GiftCertificateDto giftCertificateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRecordException("Fields of GiftCertificate has errors: " + bindingResultParser.getFieldErrMismatches(bindingResult));
        } else {
            return giftCertificateService.addGiftCertificate(giftCertificateDto);
        }
    }

    //TODO add PUT and PATCH
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificateFull(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.updateGiftCertificateFull(giftCertificateDto);
    }

//    @PatchMapping
//    @ResponseStatus(HttpStatus.OK)
//    public GiftCertificateDto updateGiftCertificatePartial(@RequestBody GiftCertificateDto giftCertificateDto) {
//        return giftCertificateService.updateGiftCertificatePartial(giftCertificateDto);
//    }
}
