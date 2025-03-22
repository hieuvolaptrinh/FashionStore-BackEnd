package com.HieuVo.BookStore_BackEnd.Controller;

import com.HieuVo.BookStore_BackEnd.DTO.PageResponse;
import com.HieuVo.BookStore_BackEnd.DTO.ProductDTO;
import com.HieuVo.BookStore_BackEnd.DTO.ResponseData;
import com.HieuVo.BookStore_BackEnd.Model.Image;
import com.HieuVo.BookStore_BackEnd.Model.Product;
import com.HieuVo.BookStore_BackEnd.Model.Type;
import com.HieuVo.BookStore_BackEnd.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {


    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{productId}/listImages")
    public ResponseEntity<Page<Image>> getProductImages(
            @PathVariable int productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Image> images = productService.getProductImages(productId, page, size);
        return ResponseEntity.ok(images);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductDTO>>getProducts(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = "5") int size
                                                     ) {
        Page<Product> products = productService.getProducts(page,size);
        // Chuyển đổi Product -> ProductDTO (đã loại bỏ vòng lặp)
        Page<ProductDTO> productDTOs = products.map(ProductDTO::new);
        // Trả về ResponseData có chứa thông tin phân tran
        return ResponseEntity.ok(
                productDTOs
        );
    }
    @GetMapping("/types")
    public ResponseEntity<List<Type>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }
}
