package com.HieuVo.FashionStore_BackEnd.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int imageId;

    private String imageName;

    private boolean icon;

    private String link;
    // cái này là optional

    @Lob
    @Column(columnDefinition = "VARBINARY(MAX)")
    private byte[] data;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "product_id", nullable = false) // ko cho null
    private Product product;

}
