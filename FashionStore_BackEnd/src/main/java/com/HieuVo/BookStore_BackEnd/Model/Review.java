package com.HieuVo.BookStore_BackEnd.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Review {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private long reviewId;

        @Column(columnDefinition = "NVARCHAR(500)")
        private String content;

        private Byte stars;

        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

}
