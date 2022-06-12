package com.epam.esm.core.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {
    private long id;

    @NotBlank
    private String name;

    @JsonIgnore
    private List<GiftCertificateDto> certificates;

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
