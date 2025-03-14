package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
}
