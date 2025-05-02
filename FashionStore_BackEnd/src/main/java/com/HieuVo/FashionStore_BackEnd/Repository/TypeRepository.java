package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {
    List<Type> findAllByTypeIdIsIn(List<Integer> id);
}