package com.HieuVo.BookStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String typeName;

    @ManyToMany(fetch = FetchType.LAZY, cascade ={
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    } )
    @JoinTable(
            name = "product_type",
            joinColumns = @JoinColumn(name = "type_id"),
            inverseJoinColumns = @JoinColumn(name ="product_id" )
    )
    private List<Product> listProducts;

}
