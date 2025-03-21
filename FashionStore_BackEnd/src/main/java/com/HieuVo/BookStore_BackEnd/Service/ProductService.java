package com.HieuVo.BookStore_BackEnd.Service;


import com.HieuVo.BookStore_BackEnd.Model.Image;
import com.HieuVo.BookStore_BackEnd.Repository.ImageRepository;
import com.HieuVo.BookStore_BackEnd.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ImageRepository imageRepository;

    public ProductService(ProductRepository productRepository , ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository=imageRepository;

    }

    public Page<Image> getProductImages(int productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return imageRepository.findByProduct_productId(productId, pageable);
    }
}
