package com.epam.esm.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private long id;

    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    @NotBlank(message = "Description shouldn't be empty")
    private String description;

    @NotNull(message = "Price shouldn't be empty")
    @Min(value = 1, message = "Price should be at least 1")
    private BigDecimal price;

    @NotNull(message = "Duration shouldn't be empty")
    @Min(value = 1, message = "Duration should be at least 1")
    private int duration;

    private String createDate;

    private String lastUpdateDate;

    private Set<TagDto> tagSet;
}
