package com.HieuVo.FashionStore_BackEnd.Repository;




import com.HieuVo.FashionStore_BackEnd.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    enpoint products sẽ có thêm /search/findByProductNameContaining/.......
    Page<Product> findByProductNameContaining(
               String productName,
                    Pageable pageable);

    Page<Product> findByListTypes_TypeIdIn(
            List<Integer> typeIds,
       Pageable pageable);

    Page<Product> findByProductNameContainingAndListTypes_TypeIdIn(String productName,
                                                                   List<Integer> typeIds,
                                                                   Pageable pageable);

    Page<Product> findAll(Pageable pageable);


}
