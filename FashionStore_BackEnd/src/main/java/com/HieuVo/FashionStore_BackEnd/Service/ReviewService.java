package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.ReviewDTO;
import com.HieuVo.FashionStore_BackEnd.Model.Review;

import com.HieuVo.FashionStore_BackEnd.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<ReviewDTO> getReviewsWithUser(int productId) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(productId);

        return reviews.stream().map(review -> {
            byte[] avatarData = review.getUser().getAvatarData();
            String avatarBase64 = avatarData != null ? Base64.getEncoder().encodeToString(avatarData) : null;

            return new ReviewDTO(
                    review.getReviewId(),
                    productId,
                    review.getContent(),
                    review.getStars(),
                    review.getUser().getFirstName() + " " + review.getUser().getLastName(),
                    avatarBase64
            );
        }).collect(Collectors.toList());
    }
}