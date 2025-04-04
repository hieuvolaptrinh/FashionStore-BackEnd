package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.ProductDTO;
import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Type;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
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

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ProductDTO(product));
    }

    @GetMapping("/{productId}/listImages")
    public ResponseEntity<List<Image>> getProductImages(
            @PathVariable int productId) {
        List<Image> images = productService.getProductImages(productId);
        return ResponseEntity.ok(images);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductDTO>> getProducts(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Page<Product> products = productService.getProducts(page, size);

        Page<ProductDTO> productDTOs = products.map(ProductDTO::new);

        return ResponseEntity.ok(
                productDTOs
        );
    }

    @GetMapping("/types")
    public ResponseEntity<List<Type>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

    @PostMapping("")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        Product created = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(created));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProduct(
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDTO> products = productService.searchProduct(typeId, productName, pageable);

        return ResponseEntity.ok(products);
    }


}
