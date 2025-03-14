package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "review-list")
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.product.productId = :productId")
    List<Review> findReviewsByProductId(@Param("productId") Integer productId);
}
