package com.HieuVo.FashionStore_BackEnd.Repository;



import com.HieuVo.FashionStore_BackEnd.Model.Image;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    enpoint products sẽ có thêm /search/findByProductNameContaining/.......
    Page<Product> findByProductNameContaining(
               String productName,
                    Pageable pageable);

    Page<Product> findByListTypes_TypeId(
       int typeId,
       Pageable pageable);

    Page<Product>findByProductNameContainingAndListTypes_TypeId( String productName,
            int typeId,
            Pageable pageable);

    Page<Product> findAll(Pageable pageable);



}
