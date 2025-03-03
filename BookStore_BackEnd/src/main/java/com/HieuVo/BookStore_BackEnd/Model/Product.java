package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int productId;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String productName;

    @Column(length = 256, columnDefinition = "NVARCHAR(256)")
    private String description;

    private double originalPrice;

    @Column(columnDefinition = "NVARCHAR(256)")
    private String author;

    private double salePrice;

    private int quantity;

    //    ngày sản xuất
    private Date manufactureDate;

    //    sao trung bình
    private float avgStars;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinTable(
            name = "product_type",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<Type> listTypes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> listImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> listReviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> listOrderDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<CartDetail> listCartDetails;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<WishListDetail> listWishListDetails;
}
