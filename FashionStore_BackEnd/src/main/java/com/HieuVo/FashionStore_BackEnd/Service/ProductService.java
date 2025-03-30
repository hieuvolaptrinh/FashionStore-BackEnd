package com.HieuVo.FashionStore_BackEnd.Service;


import com.HieuVo.FashionStore_BackEnd.DTO.ProductDTO;
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

import java.util.Base64;
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

    public Product createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setProductionInfor(dto.getProductionInfor());
        product.setSalePrice(dto.getSalePrice());
        product.setQuantity(dto.getQuantity());
        product.setManufactureDate(dto.getManufactureDate());
        product.setAvgStars(0); // Mặc định
       Product saved= productRepository.save(product);
//        gắn ảnh
        List<Image> images=dto.getListImages().stream()
                .map(base64 ->{
                    byte[] data= Base64.getDecoder().decode(base64);
                    Image image=new Image();
                    image.setData(data);
                    image.setProduct(saved);
                    return image;
                }).toList() ;

        // Gắn loại sản phẩm
        imageRepository.saveAll(images);
        List<Type> types = typeRepository.findAllById(dto.getListTypes());
        product.setListTypes(types);

        return saved;
    }
}
