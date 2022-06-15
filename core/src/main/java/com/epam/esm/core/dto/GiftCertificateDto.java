package com.epam.esm.core.dto;

import com.epam.esm.core.entity.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

//    @NotBlank(message = "Creation date shouldn't be empty")
//    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-([12]\\d|0[1-9]|3[01])([T\\s](([01]\\d|2[0-3]):[0-5]\\d|24:00)(:[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?$", message = "Creation date should be in format yyyy-MM-dd HH:mm:ss")
    private String createDate;

//    @NotBlank
//    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-([12]\\d|0[1-9]|3[01])([T\\s](([01]\\d|2[0-3]):[0-5]\\d|24:00)(:[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?$", message = "Last update date should be in format yyyy-MM-dd HH:mm:ss")
    private String lastUpdateDate;

//    @JsonManagedReference
    private Set<TagDto> tagSet;
}
