package com.epam.esm.core.dto;

import com.epam.esm.core.entity.GiftCertificate;
import com.epam.esm.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto extends RepresentationModel<OrderDto> {
    private long orderId;

    private BigDecimal finalPrice;

    private LocalDateTime purchaseTime;

    private List<GiftCertificateDto> certificates;

    private UserDto customer;
}
