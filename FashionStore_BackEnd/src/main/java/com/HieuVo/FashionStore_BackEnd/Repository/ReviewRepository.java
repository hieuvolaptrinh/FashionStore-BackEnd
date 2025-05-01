package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.product.productId = :productId")
    List<Review> findReviewsByProductId(@Param("productId") Integer productId);
//    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.productId = :productId")
//    List<Review> findReviewsByProductId(@Param("productId") int productId);
}
