package com.HieuVo.FashionStore_BackEnd.Repository;

import com.HieuVo.FashionStore_BackEnd.Model.Address;
import com.HieuVo.FashionStore_BackEnd.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(@Param("userName") String userName);

    boolean existsByEmail(@Param("email") String email);

    Optional<User> findByUserName(String userName);

    User findByEmail(String email);

    List<Address> findAddressByUserId(int userId);

}
