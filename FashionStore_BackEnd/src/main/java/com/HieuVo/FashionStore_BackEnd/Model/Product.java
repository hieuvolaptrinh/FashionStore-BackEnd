package com.HieuVo.FashionStore_BackEnd.Model;

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

        @Column(columnDefinition = "NVARCHAR(MAX)")
        private String productionInfor;

        private double salePrice;

        private int quantity;

        // ngày sản xuất
        private Date manufactureDate;

        // sao trung bình
        private float avgStars;

//        @JsonIgnore
        @ManyToMany(fetch = FetchType.LAZY, cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "type_id"))
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



        public int getProductId() {
                return productId;
        }

        public void setProductId(int productId) {
                this.productId = productId;
        }

        public String getProductName() {
                return productName;
        }

        public void setProductName(String productName) {
                this.productName = productName;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public double getOriginalPrice() {
                return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
        }

        public String getProductionInfor() {
                return productionInfor;
        }

        public void getProductionInfor(String author) {
                this.productionInfor = author;
        }

        public double getSalePrice() {
                return salePrice;
        }

        public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
        }

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public Date getManufactureDate() {
                return manufactureDate;
        }

        public void setManufactureDate(Date manufactureDate) {
                this.manufactureDate = manufactureDate;
        }

        public float getAvgStars() {
                return avgStars;
        }

        public void setAvgStars(float avgStars) {
                this.avgStars = avgStars;
        }

        public List<Type> getListTypes() {
                return listTypes;
        }

        public void setListTypes(List<Type> listTypes) {
                this.listTypes = listTypes;
        }

        public List<Image> getListImages() {
                return listImages;
        }

        public void setListImages(List<Image> listImages) {
                this.listImages = listImages;
        }

        public List<Review> getListReviews() {
                return listReviews;
        }

        public void setListReviews(List<Review> listReviews) {
                this.listReviews = listReviews;
        }

        public List<OrderDetail> getListOrderDetails() {
                return listOrderDetails;
        }

        public void setListOrderDetails(List<OrderDetail> listOrderDetails) {
                this.listOrderDetails = listOrderDetails;
        }

        public List<CartDetail> getListCartDetails() {
                return listCartDetails;
        }

        public void setListCartDetails(List<CartDetail> listCartDetails) {
                this.listCartDetails = listCartDetails;
        }

        public List<WishListDetail> getListWishListDetails() {
                return listWishListDetails;
        }

        public void setListWishListDetails(List<WishListDetail> listWishListDetails) {
                this.listWishListDetails = listWishListDetails;
        }
}
