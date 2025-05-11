package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    //    findByProduct_Id(...) nghĩa là tìm các Image có product.id = productId.
    List<Image> findByProduct_productId(int productId);

    List<Image> findAllByImageIdIsIn(List<Long> ids);

}
