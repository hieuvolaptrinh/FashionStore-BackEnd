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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private ImageRepository imageRepository;
    private TypeRepository typeRepository;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository, TypeRepository typeRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.typeRepository = typeRepository;

    }

    public List<Image> getProductImages(int productId) {
        return imageRepository.findByProduct_productId(productId);
    }

    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public List<Type> getProductTypes() {
        return typeRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.getOne(productId);
    }

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
        Product saved = productRepository.save(product);
//        gắn ảnh
        List<Image> images = dto.getListImages().stream().map(link -> {
            Image image = new Image();
            image.setLink(link); // <-- lưu link ảnh vào DB
            image.setProduct(saved);
            return image;
        }).toList();

        // Gắn loại sản phẩm
        imageRepository.saveAll(images);
        List<Type> types = typeRepository.findAllById(dto.getListTypes());
        product.setListTypes(types);

        return saved;
    }

    //    search
    public Page<ProductDTO> searchProduct(Integer typeId, String productName, Pageable pageable) {
        Page<Product> productPage;

        if (typeId != null && productName != null && !productName.isEmpty()) {
            // Tìm kiếm theo cả typeId và productName
            productPage = productRepository.findByProductNameContainingAndListTypes_TypeId(productName, typeId, pageable);
        } else if (typeId != null) {
            // Tìm kiếm theo typeId
            productPage = productRepository.findByListTypes_TypeId(typeId, pageable);
        } else if (productName != null && !productName.isEmpty()) {
            // Tìm kiếm theo productName
            productPage = productRepository.findByProductNameContaining(productName, pageable);
        } else {
            // Tìm kiếm tất cả sản phẩm nếu không có điều kiện nào
            productPage = productRepository.findAll(pageable);
        }

        // Chuyển đổi từ Page<Product> sang Page<ProductDTO>
        return productPage.map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId());
            productDTO.setProductName(product.getProductName());
            productDTO.setDescription(product.getDescription());
            productDTO.setOriginalPrice(product.getOriginalPrice());
            productDTO.setProductionInfor(product.getProductionInfor());
            productDTO.setSalePrice(product.getSalePrice());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setManufactureDate(product.getManufactureDate());
            productDTO.setAvgStars(product.getAvgStars());
            List<Image> images = imageRepository.findByProduct_productId(product.getProductId());
            List<String> listImages = images.stream().map(Image::getLink).collect(Collectors.toList());
            productDTO.setListImages(listImages);
            return productDTO;
        });
    }

}
