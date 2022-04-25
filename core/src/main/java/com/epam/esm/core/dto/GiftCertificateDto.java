package com.epam.esm.core.dto;

import com.epam.esm.core.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto {
    private long id;

    @NotBlank(message = "Name shouldn't be empty")
    private String name;

    @NotBlank(message = "Description shouldn't be empty")
    private String description;

    @NotNull(message = "Price shouldn't be empty")
    @Min(value = 1, message = "Price should be at least 1")
    private double price;

    @NotNull(message = "Duration shouldn't be empty")
    @Min(value = 1, message = "Duration should be at least 1")
    private int duration;

    @NotBlank(message = "Creation date shouldn't be empty")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-([12]\\d|0[1-9]|3[01])([T\\s](([01]\\d|2[0-3]):[0-5]\\d|24:00)(:[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?$", message = "Creation date should be in format yyyy-MM-dd HH:mm:ss")
//    @JsonDeserialize(using = JsonStringToDateDeserializer.class)
//    @JsonSerialize(using = JsonDateToStringSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String createDate;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-([12]\\d|0[1-9]|3[01])([T\\s](([01]\\d|2[0-3]):[0-5]\\d|24:00)(:[0-5]\\d([.,]\\d+)?)?([zZ]|([+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?$", message = "Last update date should be in format yyyy-MM-dd HH:mm:ss")
//    @JsonSerialize(using = JsonDateToStringSerializer.class)
//    @JsonDeserialize(using = JsonStringToDateDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String lastUpdateDate;

    private Set<Tag> tagSet;
}
