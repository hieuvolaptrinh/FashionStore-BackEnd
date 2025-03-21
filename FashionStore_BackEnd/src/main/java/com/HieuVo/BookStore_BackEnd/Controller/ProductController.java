package com.HieuVo.BookStore_BackEnd.Controller;

import com.HieuVo.BookStore_BackEnd.Model.Image;
import com.HieuVo.BookStore_BackEnd.Service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "1") int size) {
        Page<Image> images = productService.getProductImages(productId, page, size);
        return ResponseEntity.ok(images);
    }
}
