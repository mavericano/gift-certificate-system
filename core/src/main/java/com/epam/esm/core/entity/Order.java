package com.epam.esm.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long orderId;

    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToMany
    @JoinTable(
            name = "order__certificate",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    )
    private List<GiftCertificate> certificates;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @PrePersist
    public void onPrePersist() {
        setPurchaseTime(LocalDateTime.now());
    }
}
