package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.Image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ImageRepository  extends JpaRepository<Image, Integer> {
//    findByProduct_Id(...) nghĩa là tìm các Image có product.id = productId.
Page<Image> findByProduct_productId(int productId, Pageable pageable);
}
