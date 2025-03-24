package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WishListRepository extends JpaRepository<WishList, Integer> {
}
