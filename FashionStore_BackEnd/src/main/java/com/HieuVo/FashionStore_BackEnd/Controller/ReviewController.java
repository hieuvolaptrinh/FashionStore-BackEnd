package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.ReviewDTO;
import com.HieuVo.FashionStore_BackEnd.Model.Review;
import com.HieuVo.FashionStore_BackEnd.Repository.ReviewRepository;
import com.HieuVo.FashionStore_BackEnd.Service.ReviewService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

//    comment
    @PostMapping()
    @ApiMessage("thêm đánh giá thành công")
    public ResponseEntity<Void> createReview(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewDTO review, Principal principal) {
        reviewService.comment(userDetails,review);
        return ResponseEntity.ok().build();
    }

}
