package com.epam.esm.core.dto;

import com.epam.esm.core.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Date createDate;
    private Date lastUpdateDate;
    private Set<Tag> tagSet;
}
