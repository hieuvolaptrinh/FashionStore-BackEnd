package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.Request.ProductRequest;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.Notification;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.ProductResponse;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PageResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Type;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    @ApiMessage("Lấy sản phẩm thành công")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @GetMapping("/{productId}/listImages")
    @ApiMessage("Lấy danh sách ảnh sản phẩm thành công")
    public ResponseEntity<List<Image>> getProductImages(
            @PathVariable int productId) {
        List<Image> images = productService.getProductImages(productId);
        return ResponseEntity.ok(images);
    }

    @GetMapping() // trả về List hay Page đều được
    @ApiMessage("Lấy danh sách sản phẩm thành công")
    public ResponseEntity<PageResponse<ProductResponse>> getProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<ProductResponse> pageResponse = this.productService.getProducts(pageable);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/types")
    @ApiMessage("Lấy danh sách loại sản phẩm thành công")
    public ResponseEntity<List<Type>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

    //    @RequestBody và @RequestPart cùng lúc=> lỗi vì chúng yêu cầu kiểu Content-Type khác nhau
//    @PostMapping("")
//    @ApiMessage("Tạo mới sản phẩm thành công")
//    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest dto,
//                                                         @RequestPart(value = "images", required = false) List<MultipartFile> images) throws Exception {
//        Product created = productService.createProduct(dto, images);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(created));
//    }
    @PostMapping(consumes = {"multipart/form-data"})
    @ApiMessage("Tạo mới sản phẩm thành công")
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestPart("product") ProductRequest productRequest, //@RequestPart("product") để nhận JSON của ProductRequest từ phần product của multipart/form-data.
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws Exception {
        Product created = productService.createProduct(productRequest, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(created));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>> searchProduct(
            @RequestParam(name = "typeIds", required = false) String typeIdsString,
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<ProductResponse> pageResponse = productService.searchProduct(typeIdsString, productName, pageable);
        return ResponseEntity.ok(pageResponse);
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @ApiMessage("Cập nhật sản phẩm thành công")
    public ResponseEntity<Product> updateProduct(
            @Valid @RequestPart("product") ProductRequest productRequest, //@RequestPart("product") để nhận JSON của ProductRequest từ phần product của multipart/form-data.
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws Exception {
        Product result = productService.updateProduct(productRequest, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
