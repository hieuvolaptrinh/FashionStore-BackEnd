package com.HieuVo.FashionStore_BackEnd.Service;


import com.HieuVo.FashionStore_BackEnd.DTO.ProductDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PageResponse;
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

    public PageResponse<ProductDTO> getProducts(Pageable pageable ) {

        Page<Product> productPage=this.productRepository.findAll(pageable);

        Page<ProductDTO> productDTOs = productPage.map(product -> {
            // Tạo ProductDTO từ Product
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

            // Lấy danh sách hình ảnh của sản phẩm từ imageRepository
            List<Image> images = imageRepository.findByProduct_productId(product.getProductId());
            List<String> listImages = images.stream().map(Image::getLink).collect(Collectors.toList());
            productDTO.setListImages(listImages);  // Đừng quên dấu chấm phẩy ở đây!

            return productDTO;
        });

        return new PageResponse<>(productDTOs);

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
        product.setAvgStars(0);
        Product saved = productRepository.save(product);

        if(dto.getListImages() != null) {
            List<Image> images = dto.getListImages().stream().map(link -> {
                Image image = new Image();
                image.setLink(link);
                image.setProduct(saved);
                return image;
            }).toList();
            // Gắn loại sản phẩm
            imageRepository.saveAll(images);
        }
        if(dto.getListTypes()!=null) {
            List<Type> types = typeRepository.findAllById(dto.getListTypes());
            product.setListTypes(types);
        }
        return saved;
    }

    public PageResponse<ProductDTO> searchProduct(Integer typeId, String productName, Pageable pageable) {
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
        Page<ProductDTO> productDTOs = productPage.map(product -> {
            // Tạo ProductDTO từ Product
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

            // Lấy danh sách hình ảnh của sản phẩm từ imageRepository
            List<Image> images = imageRepository.findByProduct_productId(product.getProductId());
            List<String> listImages = images.stream().map(Image::getLink).collect(Collectors.toList());
            productDTO.setListImages(listImages);  // Đừng quên dấu chấm phẩy ở đây!

            return productDTO;
        });


        return new PageResponse<>(productDTOs);
    }

}
