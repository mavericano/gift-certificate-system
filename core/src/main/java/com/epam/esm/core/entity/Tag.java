package com.epam.esm.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable( name = "gift_certificate__tag",
        joinColumns = @JoinColumn(name = "tag_id"),
        inverseJoinColumns = @JoinColumn(name = "certificate_id")
    )
    @JsonBackReference
    private List<GiftCertificate> certificates;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
