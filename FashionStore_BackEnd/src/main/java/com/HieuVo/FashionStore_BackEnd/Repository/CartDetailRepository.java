package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Cart;
import com.HieuVo.FashionStore_BackEnd.Model.CartDetail;
import com.HieuVo.FashionStore_BackEnd.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    List<CartDetail> findByCart(Cart cart);
    Optional<CartDetail> findByCartAndProduct(Cart cart, Product product);

    Optional<List<CartDetail>> findByCartDetailIdIn(List<Integer> cartDetailIds);

}
