package com.HieuVo.FashionStore_BackEnd.Service;

import com.HieuVo.FashionStore_BackEnd.DTO.ReviewDTO;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Review;

import com.HieuVo.FashionStore_BackEnd.Model.User;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ReviewRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {


    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository,
                         ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

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

    public void comment(UserDetails userDetails, ReviewDTO reviewDTO) {
        User user = userRepository.findByUserName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Review review = new Review();
        Product product = this.productRepository.findById(reviewDTO.getProductId()).get();
        review.setProduct(product);
        review.setContent(reviewDTO.getContent());
        review.setStars(reviewDTO.getStars());
        review.setUser(user);

        reviewRepository.save(review);
    }
}