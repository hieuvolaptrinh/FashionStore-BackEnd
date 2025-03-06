package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
    @Column(columnDefinition = "VARCHAR(MAX)")
    private String data;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "product_id", nullable = false) // ko cho null
    private Product product;
}
