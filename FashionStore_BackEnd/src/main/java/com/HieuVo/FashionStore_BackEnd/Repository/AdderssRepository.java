package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AdderssRepository extends JpaRepository<Address, Integer> {

    List<Address> findAddressByUser_UserName(String userUserName);
    List<Address> findByUser(User user);

    List<Address> findAllByUser(User user);
}
