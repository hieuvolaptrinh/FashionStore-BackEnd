package com.HieuVo.FashionStore_BackEnd.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
public class Review {
        @Id
        @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
        private int reviewId;

      
        @Column(columnDefinition = "NVARCHAR(500)")
        private String content;

  
        @Max(value = 5, message = "Số sao tối đa là 5")
        private Byte stars;

        // @JsonIgnore
        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "product_id", nullable = false)
        private Product product;

        // @JsonIgnore
        @ManyToOne(cascade = {
                        CascadeType.PERSIST,
                        CascadeType.MERGE,
                        CascadeType.DETACH,
                        CascadeType.REFRESH
        })
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

}
