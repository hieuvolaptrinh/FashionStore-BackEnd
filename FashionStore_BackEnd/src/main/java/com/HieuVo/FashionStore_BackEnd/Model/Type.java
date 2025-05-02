package com.HieuVo.FashionStore_BackEnd.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @NotBlank(message = "Tên loại sản phẩm không được để trống")
    @Size(min = 2, max = 256, message = "Tên loại sản phẩm phải có độ dài từ 2 đến 256 ký tự")
    @Column(columnDefinition = "NVARCHAR(256)")
    private String typeName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinTable(name = "product_type", joinColumns = @JoinColumn(name = "type_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> listProducts;

    public String getTypeName() {
        return typeName;
    }
}