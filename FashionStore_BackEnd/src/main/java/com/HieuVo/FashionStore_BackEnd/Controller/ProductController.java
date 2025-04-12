package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.ProductDTO;
import com.HieuVo.FashionStore_BackEnd.DTO.Response.PageResponse;
import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import com.HieuVo.FashionStore_BackEnd.Model.Type;
import com.HieuVo.FashionStore_BackEnd.Service.ProductService;
import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
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
    @ApiMessage("Lấy sản phẩm thành công")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new ProductDTO(product));
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
    public ResponseEntity<PageResponse<ProductDTO>> getProducts(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<ProductDTO>  pageResponse =this.productService.getProducts(pageable);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/types")
    @ApiMessage("Lấy danh sách loại sản phẩm thành công")
    public ResponseEntity<List<Type>> getProductTypes() {
        return ResponseEntity.ok(productService.getProductTypes());
    }

    @PostMapping("")
    @ApiMessage("Tạo mới sản phẩm thành công")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO dto) {
        Product created = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(created));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductDTO>> searchProduct(
            @RequestParam(name = "typeId", required = false) Integer typeId,
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        PageResponse<ProductDTO> pageResponse = productService.searchProduct(typeId, productName, pageable);

        return ResponseEntity.ok(pageResponse);
    }


}
