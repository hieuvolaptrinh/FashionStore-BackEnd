package com.HieuVo.BookStore_BackEnd.Controller;

import com.HieuVo.BookStore_BackEnd.DTO.ReviewDTO;
import com.HieuVo.BookStore_BackEnd.Model.Review;
import com.HieuVo.BookStore_BackEnd.Repository.ReviewRepository;
import com.HieuVo.BookStore_BackEnd.Service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review-list")
public class ReviewController {
    private final ReviewService reviewService;
    private ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewRepository.findAll());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Integer productId) {
        List<ReviewDTO> reviews = reviewService .getReviewsWithUser(productId);
        return ResponseEntity.ok(reviews);
    }
}
