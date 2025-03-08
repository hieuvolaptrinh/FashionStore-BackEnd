package com.HieuVo.BookStore_BackEnd.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Type {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int typeId;

        @Column(columnDefinition = "NVARCHAR(256)")
        private String typeName;

        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinTable(name = "product_type", joinColumns = @JoinColumn(name = "type_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
        private List<Product> listProducts;


        public int getTypeId() {
                return typeId;
        }

        public void setTypeId(int typeId) {
                this.typeId = typeId;
        }

        public String getTypeName() {
                return typeName;
        }

        public void setTypeName(String typeName) {
                this.typeName = typeName;
        }

        public List<Product> getListProducts() {
                return listProducts;
        }

        public void setListProducts(List<Product> listProducts) {
                this.listProducts = listProducts;
        }
}
