package com.HieuVo.BookStore_BackEnd.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private int reviewId;
    private String content;
    private float stars;
    private UserDTO user;

    public ReviewDTO(int reviewId, String content, float stars, UserDTO user) {
        this.reviewId = reviewId;
        this.content = content;
        this.stars = stars;
        this.user = user;
    }

    // Getters & Setters

}
