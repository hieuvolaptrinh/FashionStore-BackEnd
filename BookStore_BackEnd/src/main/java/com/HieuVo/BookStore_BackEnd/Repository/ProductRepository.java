package com.HieuVo.BookStore_BackEnd.Repository;



import com.HieuVo.BookStore_BackEnd.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByProductNameContaining(@RequestParam("productName") String productName, Pageable pageable);
}
