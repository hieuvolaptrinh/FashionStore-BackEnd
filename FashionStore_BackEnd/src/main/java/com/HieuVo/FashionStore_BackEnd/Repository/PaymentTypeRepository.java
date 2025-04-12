package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
    List<PaymentType> findAll();
}
