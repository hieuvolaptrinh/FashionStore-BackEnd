package com.HieuVo.BookStore_BackEnd.Repository;


import com.HieuVo.BookStore_BackEnd.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
