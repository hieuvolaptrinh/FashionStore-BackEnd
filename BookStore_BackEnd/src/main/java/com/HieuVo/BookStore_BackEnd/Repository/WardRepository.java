package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface WardRepository extends JpaRepository<Ward, Integer> {
}
