package com.HieuVo.BookStore_BackEnd.Repository;

import com.HieuVo.BookStore_BackEnd.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ImageRepository  extends JpaRepository<Image, Integer> {
}
