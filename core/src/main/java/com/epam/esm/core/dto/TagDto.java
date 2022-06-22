package com.epam.esm.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto extends RepresentationModel<TagDto> {
    private long id;

    @NotBlank
    private String name;

    private LocalDateTime createDate;

    @JsonIgnore
    private List<GiftCertificateDto> certificates;
}
