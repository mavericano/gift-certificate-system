package com.epam.esm.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
