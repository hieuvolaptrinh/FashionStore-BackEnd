package com.HieuVo.FashionStore_BackEnd.Service;


import com.HieuVo.FashionStore_BackEnd.DTO.Request.ProductRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ProductResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PageResponse;

import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Type;
import com.HieuVo.FashionStore_BackEnd.Repository.ImageRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.ProductRepository;
import com.HieuVo.FashionStore_BackEnd.Repository.TypeRepository;
import com.HieuVo.FashionStore_BackEnd.Util.GoogleDriveUploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final TypeRepository typeRepository;
    private final GoogleDriveUploader googleDriveUploader;

    public ProductService(ProductRepository productRepository, ImageRepository imageRepository,
                          TypeRepository typeRepository, GoogleDriveUploader googleDriveUploader) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.typeRepository = typeRepository;
        this.googleDriveUploader = googleDriveUploader;

    }

    public List<Image> getProductImages(int productId) {
        return imageRepository.findByProduct_productId(productId);
    }

    public PageResponse<ProductResponse> getProducts(Pageable pageable) {
        Page<Product> productPage = this.productRepository.findAll(pageable);

        Page<ProductResponse> productDTOs = productPage.map(product -> {
            // Tạo ProductDTO từ Product
            ProductResponse productDTO = new ProductResponse(product);
            return productDTO;
        });

        return new PageResponse<>(productDTOs);

    }

    public List<Type> getProductTypes() {
        return typeRepository.findAll();
    }

    public Product getProductById(int productId) {
        return productRepository.findById(productId).get();
    }

    @Transactional
    public Product createProduct(ProductRequest dto, List<MultipartFile> images) throws Exception {
        // Kiểm tra dữ liệu đầu vào
        if (dto == null) {
            throw new IllegalArgumentException("ProductRequest không được null");
        }

        // Tạo Product
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setProductionInfor(dto.getProductionInfor());
        product.setSalePrice(dto.getSalePrice());
        product.setQuantity(dto.getQuantity());
        product.setManufactureDate(dto.getManufactureDate());
        product.setAvgStars(0);

        // Gán danh sách Type
        if (dto.getListTypes() != null && !dto.getListTypes().isEmpty()) {
            List<Type> types = typeRepository.findAllByTypeIdIsIn(dto.getListTypes());
            if (types.size() != dto.getListTypes().size()) {
                throw new IllegalArgumentException("Một số Type không tồn tại");
            }
            product.setListTypes(types);
        }

        // Xử lý hình ảnh
        List<Image> imageList = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile file : images) {
                // Kiểm tra file
                if (file.isEmpty()) {
                    throw new IllegalArgumentException("Có file hình ảnh rỗng: " + file.getOriginalFilename());
                }
                String contentType = file.getContentType();
                if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
                    throw new IllegalArgumentException("Chỉ hỗ trợ định dạng JPEG hoặc PNG: " + contentType);
                }

                // Tạo file tạm (nếu uploadImageToDrive vẫn yêu cầu File)
                File tempFile = File.createTempFile("temp", null);
                try {
                    file.transferTo(tempFile);

                    // Tải file lên Google Drive
                    String link = googleDriveUploader.uploadImageToDrive(tempFile);

                    // Tạo Image
                    Image image = new Image();
                    image.setLink(link);
                    image.setProduct(product);
                    imageList.add(image);
                } finally {
                    // Xóa file tạm
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                }
            }
            product.setListImages(imageList); // Gán vào Product để cascade lưu
        }

        // Lưu Product (tự động lưu Images nhờ cascade)
        return productRepository.save(product);
    }
    public PageResponse<ProductResponse> searchProduct(Integer typeId, String productName, Pageable pageable) {
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
        Page<ProductResponse> productDTOs = productPage.map(product -> {
            // Tạo ProductDTO từ Product
            ProductResponse productDTO = new ProductResponse(product);
            return productDTO;
        });


        return new PageResponse<>(productDTOs);
    }


    public String updateProduct(ProductRequest dto) {

        Product product = productRepository.findById(dto.getProductId()).get();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setProductionInfor(dto.getProductionInfor());
        product.setSalePrice(dto.getSalePrice());
        product.setQuantity(dto.getQuantity());
        product.setManufactureDate(dto.getManufactureDate());

//        // Xóa ảnh cũ
//        if (dto.getListImages() != null) {
//            List<Image> images = imageRepository.findByProduct_productId(dto.getProductId());
//            for (Image image : images) {
//                imageRepository.delete(image);
//            }
//            List<Image> newImages = dto.getListImages().stream().map(link -> {
//                Image image = new Image();
//                image.setLink(link);
//                image.setProduct(product);
//                return image;
//            }).toList();
//            imageRepository.saveAll(newImages);
//        }
        if (dto.getListTypes() != null) {
            List<Type> types = typeRepository.findAllById(dto.getListTypes());
            product.setListTypes(types);
        }
        productRepository.save(product);
        return "Cập nhật sản phẩm thành công";
    }
}
