package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "review-list")
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
