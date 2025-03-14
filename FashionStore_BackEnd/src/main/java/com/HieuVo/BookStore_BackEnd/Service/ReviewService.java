package com.HieuVo.BookStore_BackEnd.Service;

import com.HieuVo.BookStore_BackEnd.DTO.ReviewDTO;
import com.HieuVo.BookStore_BackEnd.DTO.UserDTO;
import com.HieuVo.BookStore_BackEnd.Model.Review;
import com.HieuVo.BookStore_BackEnd.Repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> getReviewsWithUser(Integer productId) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(productId);

        return reviews.stream().map(review ->
                new ReviewDTO(
                        review.getReviewId(),
                        review.getContent(),
                        review.getStars(),
                        new UserDTO(
                                review.getUser().getFirstName(),
                                review.getUser().getLastName(),
                                review.getUser().getEmail()
                        )
                )
        ).collect(Collectors.toList());
    }
}