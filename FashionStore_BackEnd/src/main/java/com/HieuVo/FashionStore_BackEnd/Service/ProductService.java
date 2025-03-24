package com.HieuVo.FashionStore_BackEnd.Service;


import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Type;
import com.HieuVo.FashionStore_BackEnd.Repository.ImageRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.TypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private TypeRepository typeRepository;

    public ProductService(ProductRepository productRepository , ImageRepository imageRepository, TypeRepository typeRepository) {
        this.productRepository = productRepository;
        this.imageRepository=imageRepository;
        this.typeRepository=typeRepository;

    }

    public Page<Image> getProductImages(int productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return imageRepository.findByProduct_productId(productId, pageable);
    }
    public Page<Product> getProducts( int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }
    public List<Type> getProductTypes() {
        return typeRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.getOne(productId);}
}
