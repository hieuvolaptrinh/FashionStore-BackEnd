package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Review {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int reviewId;

        @Column(columnDefinition = "NVARCHAR(500)")
        private String content;

        private Byte stars;


//        @JsonIgnore
        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;


//        @JsonIgnore
        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        public int getReviewId() {
                return reviewId;
        }

        public void setReviewId(int reviewId) {
                this.reviewId = reviewId;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public Byte getStars() {
                return stars;
        }

        public void setStars(Byte stars) {
                this.stars = stars;
        }

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }
}
